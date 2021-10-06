package com.torrydo.transe.adapter.vocabSource.model

data class SearchResult(
    val vocab: String,
    val destination: Int,
    val type: String,
    val pronun: Pronunciation,
    val searchResultItemList: List<SearchResultItem>
)
