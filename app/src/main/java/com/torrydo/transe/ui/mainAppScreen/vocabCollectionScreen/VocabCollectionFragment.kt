package com.torrydo.transe.ui.mainAppScreen.vocabCollectionScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.torrydo.transe.R
import com.torrydo.transe.dataSource.database.local.models.Vocab
import com.torrydo.transe.databinding.FragmentVocabCollectionBinding
import com.torrydo.transe.databinding.ItemVocabCollectionBinding
import com.torrydo.transe.interfaces.VocabListenter
import com.torrydo.transe.listAdapter.base.GenericAdapter
import com.torrydo.transe.listAdapter.holderClass.VocabCollectionHolder
import com.torrydo.transe.service.searchService.LauncherSearchService
import com.torrydo.transe.ui.base.BaseFragment
import com.torrydo.transe.ui.mainAppScreen.MainViewModel
import com.torrydo.transe.utils.CONSTANT
import com.torrydo.transe.utils.MyPopupMenuHelper
import com.torrydo.transe.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
@Named(CONSTANT.activityModule)
class VocabCollectionFragment :
    BaseFragment<VocabCollectionViewModel, FragmentVocabCollectionBinding>() {

    companion object {
        var TAB_FINISHED = false
    }

    @Inject
    @Named(CONSTANT.activityPopupMenuHelper)
    lateinit var popupMenuHelper: MyPopupMenuHelper

    private val activityViewModel: MainViewModel by activityViewModels()
    private val mViewModel: VocabCollectionViewModel by viewModels()

    private var mAdapterVocab: GenericAdapter<Vocab>? = null


    override fun getViewModelClass() = mViewModel
    override fun getViewBinding() = FragmentVocabCollectionBinding.inflate(layoutInflater)


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
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.main_color_lighter)

        mAdapterVocab = object : GenericAdapter<Vocab>(null) {
            override fun getLayoutId(position: Int, obj: Vocab): Int {
                return R.layout.item_vocab_collection
            }

            override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
                VocabCollectionHolder(
                    ItemVocabCollectionBinding.inflate(
                        LayoutInflater.from(requireContext()),
                        parent,
                        false
                    ),
                    MyVocabListener()
                )


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

        binding.vocabMainTextType.setOnClickListener {
            if (TAB_FINISHED) {
                TAB_FINISHED = false
                binding.vocabMainTextType.text = "Not Finished"

                activityViewModel.vocabListNotFinished.value?.let {
                    updateListToAdapter(it)
                    return@setOnClickListener
                }
                Utils.showShortToast(requireContext(), "list null")
            } else {
                TAB_FINISHED = true
                binding.vocabMainTextType.text = "Finished"

                activityViewModel.vocabListFinished.value?.let {
                    updateListToAdapter(it)
                    return@setOnClickListener
                }
                Utils.showShortToast(requireContext(), "list null")
            }
        }

        binding.vocabRecycler3dots.setOnClickListener { v ->
            popupMenuHelper.show(
                v,
                listOf(
                    "Upload",  // 0
                    "Sync",    // 1
                    "Shuffe",  // 2
                )
            ) { position ->
                when (position) {
                    0 -> {
                        if (vocabListOfficial.isNotEmpty()) {
                            viewModel.insertAllToRemoteDatabase(vocabListOfficial)
                        } else {
                            Utils.showShortToast(requireContext(), "không có gì sao upload?")
                        }

                    }
                    1 -> {
                        Utils.showLongToast(requireContext(), "syncing")
                        viewModel.syncAllVocabFromRemoteDatabase(vocabListOfficial) {
                            Utils.showShortToast(requireContext(), "fck you")
                        }

                    }
                    2 -> {
                        activityViewModel.shuffle2VocabList() {
                            update2VocabListState()
                            Utils.showShortToast(requireContext(), "Updated")
                        }
                    }
                }
            }
        }
    }

    var vocabListOfficial = ArrayList<Vocab>()
    private fun observeLiveData() {
        activityViewModel.vocabLiveData.observe(viewLifecycleOwner, { vl ->
            if (vocabListOfficial.isNotEmpty()) {
                vocabListOfficial.clear()
            }
            vocabListOfficial.addAll(vl)

            activityViewModel.set2VocabList() {  // when IO Thread finished it's work
                update2VocabListState()
            }

        })
    }

    private fun update2VocabListState() {
        if (TAB_FINISHED) {
            binding.vocabMainTextType.text = "Finished"
            activityViewModel.vocabListFinished.value?.let {
                updateListToAdapter(it)
            }
        } else {
            binding.vocabMainTextType.text = "Not Finished"
            activityViewModel.vocabListNotFinished.value?.let {
                updateListToAdapter(it)
            }
        }
    }

    private fun updateListToAdapter(list: List<Vocab>) {
        mAdapterVocab?.setItems(list)
    }

    // --------------------------------------------------------

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

    // define some events for recyclerItem
    private inner class MyVocabListener : VocabListenter {

        override fun onTouch(position: Int) {
            activityViewModel.TAB_POSITION = position

            requireActivity().findNavController(R.id.mainFragmentContainerView)
                .navigate(R.id.action_vocabCollectionFragment_to_vocabFragment)
        }

        override fun update(vocab: Vocab) {
            activityViewModel.updateVocab(vocab)
            viewModel.updateVocabFromRemoteDB(vocab)

            update2VocabListState()

            Utils.showShortToast(requireContext(), "working on it")
        }

        override fun delete(vocab: Vocab) {
            activityViewModel.deleteVocab(vocab)
            viewModel.deleteVocabFromRemoteDB(vocab)

            update2VocabListState()

            Utils.showShortToast(requireContext(), "deleting")
        }

//        override fun playPronunciation(keyWord: String, pronunciation: Pronunciation) {
//            pronunciationHelper.playAudio(keyWord, pronunciation)
//        }
    }

}