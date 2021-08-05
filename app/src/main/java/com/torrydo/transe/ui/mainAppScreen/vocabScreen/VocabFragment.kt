package com.torrydo.transe.ui.mainAppScreen.vocabScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.torrydo.transe.R
import com.torrydo.transe.adapter.base.GenericAdapter
import com.torrydo.transe.adapter.holderClass.VocabHolder
import com.torrydo.transe.dataSource.database.local.models.Vocab
import com.torrydo.transe.dataSource.translation.eng.pronunciation.PronunciationHelper
import com.torrydo.transe.dataSource.translation.eng.pronunciation.models.Pronunciation
import com.torrydo.transe.databinding.FragmentVocabBinding
import com.torrydo.transe.databinding.ItemVocabBinding
import com.torrydo.transe.interfaces.VocabListenter
import com.torrydo.transe.service.searchService.LauncherSearchService
import com.torrydo.transe.ui.base.BaseFragment
import com.torrydo.transe.utils.CONSTANT
import com.torrydo.transe.utils.MyPopupMenuHelper
import com.torrydo.transe.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
@Named(CONSTANT.activityModule)
class VocabFragment : BaseFragment<VocabViewModel, FragmentVocabBinding>() {

    @Inject
    @Named(CONSTANT.activityPronunciation)
    lateinit var pronunciationHelper: PronunciationHelper

    @Inject
    @Named(CONSTANT.activityPopupMenuHelper)
    lateinit var popupMenuHelper: MyPopupMenuHelper

    private val mViewModel: VocabViewModel by viewModels()

    private var mAdapterVocab: GenericAdapter<Vocab>? = null
    private var vocabList: ArrayList<Vocab> = ArrayList()

    override fun getViewModelClass() = mViewModel
    override fun getViewBinding(): FragmentVocabBinding =
        FragmentVocabBinding.inflate(layoutInflater)

    override fun configOnCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        setup()
        requestOnCLick()
        observeLiveData()
    }

    private fun setup() {

        mAdapterVocab = object : GenericAdapter<Vocab>(vocabList) {
            override fun getLayoutId(position: Int, obj: Vocab): Int {
                return R.layout.item_vocab
            }

            override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return VocabHolder(
                    ItemVocabBinding.inflate(
                        LayoutInflater.from(requireContext()),
                        parent,
                        false
                    ),
                    MyVocabListener()
                )
            }

        }

        binding.vocabRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = mAdapterVocab
        }

    }


    private fun requestOnCLick() {
        binding.vocabMainButton.setOnClickListener {
            startService()
            requireActivity().finish()
        }

        binding.vocabRecycler3dots.setOnClickListener { v ->
            popupMenuHelper.show(
                v,
                listOf(
                    "Upload",  // 0
                    "Sync",    // 1
                )
            ) { position ->
                when (position) {
                    0 -> {
                        Utils.showLongToast(requireContext(), "updating to remoteDatabase")
                        viewModel.resultList.value?.let { vocabList ->
                            viewModel.insertAllToRemoteDatabase(vocabList)

                        }
                    }
                    1 -> {
                        viewModel.syncAllVocabFromRemoteDatabase()
                    }
                }
            }
        }

    }

    private fun observeLiveData() {
        mViewModel.resultList.observe(viewLifecycleOwner, {
            if (vocabList.isNotEmpty()) {
                vocabList.clear()
            }
            vocabList.addAll(it)
            mAdapterVocab?.apply {
                setItems(it)
            }
        })
    }

    private fun startService() {
        val intent = Intent(requireContext(), LauncherSearchService::class.java).apply {
            putExtra(CONSTANT.DEVICE_WIDTH, Utils.getDeviceScreenInfo(requireActivity(), true))
            putExtra(
                CONSTANT.DEVICE_HEIGHT,
                Utils.getDeviceScreenInfo(requireActivity(), false)
            )
        }
        requireContext().startService(intent)
    }

    private inner class MyVocabListener : VocabListenter {
        override fun delete(vocab: Vocab) {
            mViewModel.deleteVocab(vocab)
            Utils.showShortToast(requireContext(), "deleting")
        }

        override fun playPronunciation(pronunciation: Pronunciation) {
            pronunciationHelper.playAudio(pronunciation)
        }
    }

}