package com.torrydo.transe.listAdapter.holderClass

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.torrydo.transe.R
import com.torrydo.transe.adapter.vocabSource.model.SearchResult
import com.torrydo.transe.adapter.vocabSource.model.SearchResultItem
import com.torrydo.transe.databinding.ItemInnerResultBinding
import com.torrydo.transe.databinding.ItemResultBinding
import com.torrydo.transe.interfaces.VocabListenter
import com.torrydo.transe.listAdapter.base.GenericAdapter
import com.torrydo.transe.utils.CONSTANT

class ResultHolder(
    private val binding: ItemResultBinding,
    private val vocabListenter: VocabListenter
) :
    RecyclerView.ViewHolder(binding.root),
    GenericAdapter.Binder<SearchResult> {

    private fun requestOnClick(item: SearchResult) {
        binding.itemResultAudio.setOnClickListener {
//            vocabListenter.playPronunciation(
//                CONSTANT.KeyWord_Holder,
//                item.pronunciation
//            )
            Log.i("item click", "play pronun is being maintain")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun bind(item: SearchResult, position: Int) {
        binding.itemResultType.also { tv ->
            val tempType = item.type
            when (tempType) {
                CONSTANT.NOUN -> {
                    tv.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.noun)
                    )
                }
                CONSTANT.VERB -> {
                    tv.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.verb)
                    )
                }
                CONSTANT.ADJ -> {
                    tv.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.adjective)
                    )
                }
            }
            tv.text = "[$tempType]"
        }
        binding.itemResultPronounce.text =
            "/" + item.pronun.text + "/"

        val tempList = ArrayList<SearchResultItem>()
        tempList.addAll(item.searchResultItemList)

        initInnerRecyclerView(
            binding.root.context,
            binding.itemResultInnerRecyclerView,
            tempList
        )

        requestOnClick(item)

    }

    private fun initInnerRecyclerView(
        context: Context,
        recyclerView: RecyclerView,
        innerEngResultList: ArrayList<SearchResultItem>
    ) {
        val adapterInnerResult = object : GenericAdapter<SearchResultItem>(innerEngResultList) {
            override fun getLayoutId(position: Int, obj: SearchResultItem): Int {
                return R.layout.item_inner_result
            }

            override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return InnerResultHolder(
                    ItemInnerResultBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapterInnerResult
    }


}