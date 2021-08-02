package com.torrydo.transe.adapter.holderClass

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.torrydo.transe.utils.CONSTANT
import com.torrydo.transe.R
import com.torrydo.transe.adapter.base.GenericAdapter
import com.torrydo.transe.databinding.ItemInnerResultBinding
import com.torrydo.transe.databinding.ItemResultBinding
import com.torrydo.transe.interfaces.VocabListenter
import com.torrydo.transe.dataSource.data.eng.models.InnerEngResult
import com.torrydo.transe.dataSource.data.eng.models.EngResult

class ResultHolder(
    private val binding: ItemResultBinding,
    private val vocabListenter: VocabListenter
) :
    RecyclerView.ViewHolder(binding.root),
    GenericAdapter.Binder<EngResult> {

    private fun requestOnClick(item: EngResult) {
        binding.itemResultAudio.setOnClickListener {
            vocabListenter.playPronunciation(item.pronunciation)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun bind(item: EngResult) {
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
            "/" + item.pronunciation.text + "/"

        val tempList = ArrayList<InnerEngResult>()
        tempList.addAll(item.innerEngResultList)

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
        innerEngResultList: ArrayList<InnerEngResult>
    ) {
        val adapterInnerResult = object : GenericAdapter<InnerEngResult>(innerEngResultList) {
            override fun getLayoutId(position: Int, obj: InnerEngResult): Int {
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