package com.torrydo.transe.adapter.vocabSource.model

data class SearchResult (
    val vocab: String,
    val pronun: Pronunciation,
    val searchResultItemList: List<SearchResultItem?>
)
