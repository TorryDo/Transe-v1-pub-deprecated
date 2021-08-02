package com.torrydo.transeng.adapter.holderClass

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.torrydo.transeng.adapter.base.GenericAdapter
import com.torrydo.transeng.databinding.ItemInnerResultBinding
import com.torrydo.transeng.dataSource.data.eng.models.InnerEngResult

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

    override fun bind(item: InnerEngResult) {
        binding.itemInnerResultTxtTitle.text = item.title
        binding.itemInnerResultTxtContent.text = item.examples

        requestOnClick()

    }

    private fun showContent(showThis: View, hideThis: View) {
        showThis.visibility = View.VISIBLE
        hideThis.visibility = View.GONE
    }
}