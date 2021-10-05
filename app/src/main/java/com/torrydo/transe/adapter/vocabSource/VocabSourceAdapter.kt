package com.torrydo.transe.adapter.vocabSource

import com.torrydo.transe.adapter.vocabSource.model.Pronunciation
import com.torrydo.transe.adapter.vocabSource.model.SearchResult
import com.torrydo.transe.adapter.vocabSource.model.SearchResultItem
import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.vocabsource.VocabSource
import com.torrydo.vocabsource.eng.models.EngResult

class VocabSourceAdapter(private val vocabSource: VocabSource) : VocabTranslator {

    override fun search(keyWord: String, listResultListener: ListResultListener) {
        try {
            vocabSource.search(keyWord) {
                listResultListener.onSuccess(it.map { engResult -> engResult?.toSearchResult() })
            }
        } catch (e: Exception) {
            listResultListener.onError(e)
        }
    }

    // adapt from module object to searchResult
    private fun EngResult.toSearchResult(): SearchResult {
        return SearchResult(
            vocab = this.vocab,
            pronun = Pronunciation(
                this.pronunciation.text,
                this.pronunciation.url
            ),
            searchResultItemList = this.innerEngResultList.map { innerEngResult ->
                SearchResultItem(
                    innerEngResult.title,
                    innerEngResult.examples
                )
            }
        )
    }
}