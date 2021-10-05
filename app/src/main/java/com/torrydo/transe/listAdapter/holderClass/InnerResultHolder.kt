package com.torrydo.transe.listAdapter.holderClass

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.torrydo.transe.listAdapter.base.GenericAdapter
import com.torrydo.transe.databinding.ItemInnerResultBinding
import com.torrydo.transe.dataSource.translation.eng.models.InnerEngResult

class InnerResultHolder(
    private val binding: ItemInnerResultBinding
) :
    RecyclerView.ViewHolder(binding.root),
    GenericAdapter.Binder<InnerEngResult>
{

    private fun requestOnClick() {
        binding.itemInnerResultButtonExamples.setOnClickListener {
            showContent(
                binding.itemInnerResultTxtContent,
                binding.itemInnerResultButtonExamples
            )
        }
        binding.itemInnerResultTxtContent.setOnClickListener {
            showContent(
                binding.itemInnerResultButtonExamples,
                binding.itemInnerResultTxtContent
            )
        }
    }

    override fun bind(item: InnerEngResult, position: Int) {
        binding.itemInnerResultTxtTitle.text = item.title
        binding.itemInnerResultTxtContent.text = item.examples

        requestOnClick()

    }

    private fun showContent(showThis: View, hideThis: View) {
        showThis.visibility = View.VISIBLE
        hideThis.visibility = View.GONE
    }
}