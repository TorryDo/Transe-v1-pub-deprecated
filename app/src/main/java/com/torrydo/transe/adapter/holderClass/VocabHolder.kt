package com.torrydo.transe.adapter.holderClass

import androidx.recyclerview.widget.RecyclerView
import com.torrydo.transe.adapter.base.GenericAdapter
import com.torrydo.transe.dataSource.database.local.models.Vocab
import com.torrydo.transe.databinding.ItemVocabBinding
import com.torrydo.transe.interfaces.VocabListenter

class VocabHolder(
    val binding: ItemVocabBinding,
    private val vocabListenter: VocabListenter
):
    RecyclerView.ViewHolder(binding.root),
    GenericAdapter.Binder<Vocab>
{
    override fun bind(item: Vocab) {
        binding.itemVocabTitle.text = item.vocab
    }


}