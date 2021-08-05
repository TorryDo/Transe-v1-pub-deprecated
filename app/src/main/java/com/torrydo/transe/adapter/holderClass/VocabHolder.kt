package com.torrydo.transe.adapter.holderClass

import android.content.res.ColorStateList
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.torrydo.transe.R
import com.torrydo.transe.adapter.base.GenericAdapter
import com.torrydo.transe.dataSource.database.local.models.Vocab
import com.torrydo.transe.databinding.ItemVocabBinding
import com.torrydo.transe.interfaces.VocabListenter
import com.torrydo.transe.utils.CONSTANT
import com.torrydo.transe.utils.MyPopupMenuHelper

class VocabHolder(
    private val viewBinding: ItemVocabBinding,
    private val vocabListenter: VocabListenter
) :
    RecyclerView.ViewHolder(viewBinding.root),
    GenericAdapter.Binder<Vocab> {

    val popUpHelper = MyPopupMenuHelper(viewBinding.root.context)

    private fun requestOnClick(item: Vocab) {
        viewBinding.itemVocabTitle.setOnClickListener { view ->
            vocabListenter.playPronunciation(item.vocab, item.contentEng[0].pronunciation)
        }

        viewBinding.itemVocab3dots.setOnClickListener { view ->

            popUpHelper.show(
                view,
                listOf(
                    "Delete",  // 0
                )
            ) { position ->
                when (position) {
                    0 -> vocabListenter.delete(item)
                }
            }

        }
    }

    override fun bind(item: Vocab) {

        val contentBuilder = StringBuilder()

        val rsList = item.contentEng

        var nounCount = 0f
        var verbCount = 0f
        var adjCount = 0f

        val viewTypeList = arrayListOf(
            viewBinding.itemVocabView1,
            viewBinding.itemVocabView2,
            viewBinding.itemVocabView3
        )

        val typeList = ArrayList<String>()

        viewBinding.itemVocabTitle.text = item.vocab

        for (engRsList in rsList) {
            val type = engRsList.type
            if (type == CONSTANT.NOUN || type == CONSTANT.VERB || type == CONSTANT.ADJ) {
                typeList.add(type)
            }

            when (type) {
                CONSTANT.NOUN -> {
                    nounCount = engRsList.innerEngResultList.size.toFloat()
                }
                CONSTANT.VERB -> {
                    verbCount = engRsList.innerEngResultList.size.toFloat()
                }
                CONSTANT.ADJ -> {
                    adjCount = engRsList.innerEngResultList.size.toFloat()
                }
            }
            contentBuilder.append("• $type : ${engRsList.innerEngResultList[0].title}\n")

        }

        viewBinding.itemVocabContent.text = contentBuilder.toString()

        var sum3 = nounCount + verbCount + adjCount
        if (sum3 == 0f) {
            sum3 = 1f
        }
        nounCount /= sum3
        verbCount /= sum3
        adjCount /= sum3

        for (i in 0..2) {
            val singleType = try {
                typeList[i]
            } catch (e: Exception) {
                ""
            }
            viewTypeList[i].setColorState(singleType, nounCount, verbCount, adjCount)
        }

        requestOnClick(item)

    }

    private fun View.setColorState(
        type: String,
        nounCount: Float,
        verbCount: Float,
        adjCount: Float
    ) {
        val param = this.layoutParams as ConstraintLayout.LayoutParams
        when (type) {

            CONSTANT.NOUN -> {
                this.setBackgroundViewTint(R.color.noun)

                param.horizontalWeight = nounCount
                this.layoutParams = param
            }
            CONSTANT.VERB -> {
                this.setBackgroundViewTint(R.color.verb)

                param.horizontalWeight = verbCount
                this.layoutParams = param
            }
            CONSTANT.ADJ -> {
                this.setBackgroundViewTint(R.color.adjective)

                param.horizontalWeight = adjCount
                this.layoutParams = param
            }
            else -> {
                param.horizontalWeight = 0f
                this.layoutParams = param
            }
        }
    }

    private fun View.setBackgroundViewTint(color: Int) {
        this.backgroundTintList =
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    color
                )
            )
    }


}