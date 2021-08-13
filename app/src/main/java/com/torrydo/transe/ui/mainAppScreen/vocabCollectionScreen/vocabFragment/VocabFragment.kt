package com.torrydo.transe.ui.mainAppScreen.vocabCollectionScreen.vocabFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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
import com.torrydo.transe.ui.base.BaseFragment
import com.torrydo.transe.ui.mainAppScreen.MainViewModel
import com.torrydo.transe.ui.mainAppScreen.vocabCollectionScreen.VocabCollectionViewModel
import com.torrydo.transe.utils.CONSTANT
import com.torrydo.transe.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
@Named(CONSTANT.fragmentModule)
class VocabFragment : BaseFragment<VocabCollectionViewModel, FragmentVocabBinding>() {

    @Inject
    @Named(CONSTANT.fragmentPronunciation)
    lateinit var pronunciationHelper: PronunciationHelper

    private val activityViewModel: MainViewModel by activityViewModels()
    private val mVM: VocabCollectionViewModel by viewModels()

    private var adapter: GenericAdapter<Vocab>? = null

    override fun getViewModelClass() = mVM
    override fun getViewBinding() = FragmentVocabBinding.inflate(layoutInflater)

    override fun configOnCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        setup()
    }

    private fun setup() {
        activityViewModel.vocabLiveData.value?.let { vl ->
            adapter = object : GenericAdapter<Vocab>(vl) {

                override fun getLayoutId(position: Int, obj: Vocab) = R.layout.item_vocab
                override fun getViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): RecyclerView.ViewHolder = VocabHolder(
                    binding = ItemVocabBinding.inflate(layoutInflater, parent, false),
                    vocabListenter = MyVocabListener()
                )

            }

            binding.vocabViewPager2.adapter = adapter
        }

    }

    // define listener for vocabItem
    private inner class MyVocabListener : VocabListenter {

        override fun update(vocab: Vocab) {
            activityViewModel.updateVocab(vocab)
            viewModel.updateVocabFromRemoteDB(vocab)

            Utils.showShortToast(requireContext(), "working on it")
        }

        override fun delete(vocab: Vocab) {
            activityViewModel.deleteVocab(vocab)
            viewModel.deleteVocabFromRemoteDB(vocab)

            Utils.showShortToast(requireContext(), "deleting")
        }

        override fun playPronunciation(keyWord: String, pronunciation: Pronunciation) {
            pronunciationHelper.playAudio(keyWord, pronunciation)
        }
    }
}