package com.torrydo.transe.listAdapter.holderClass

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.torrydo.transe.R
import com.torrydo.transe.adapter.vocabSource.model.SearchResult
import com.torrydo.transe.listAdapter.base.GenericAdapter
import com.torrydo.transe.dataSource.database.local.models.Vocab
import com.torrydo.transe.databinding.ItemResultBinding
import com.torrydo.transe.databinding.ItemVocabBinding
import com.torrydo.transe.interfaces.VocabListenter

class VocabHolder(
    val binding: ItemVocabBinding,
    private val vocabListenter: VocabListenter
) :
    RecyclerView.ViewHolder(binding.root),
    GenericAdapter.Binder<Vocab> {

//    private val pronunciationHelper = PronunciationHelper(binding.root.context)

    override fun bind(item: Vocab, position: Int) {
        binding.itemVocabTitle.text = item.vocab

        initRecyclerView(item.contentEng)

    }

    private fun initRecyclerView(engResultList: List<SearchResult>) {

        val adapterEng = object : GenericAdapter<SearchResult>(engResultList) {
            override fun getLayoutId(position: Int, obj: SearchResult): Int {
                return R.layout.item_result
            }

            override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return ResultHolder(
                    ItemResultBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    MyVocabListener()
                )
            }
        }

        binding.itemVocabReyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = adapterEng
        }
    }

    private inner class MyVocabListener : VocabListenter {
        override fun playPronunciation(keyWord: String/*, pronunciation: Pronunciation*/) {
//            pronunciationHelper.playAudio(keyWord, pronunciation)
            Log.i("VocabHolder", "fake play pronun")
        }
    }


}