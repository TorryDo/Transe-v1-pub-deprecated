package com.torrydo.transe.adapter.vocabSource.model

data class SearchResult(
    val vocab: String,              // eg : keyword, star, ball
    val destination: Int,           // eg : english/vietnamese/japanese  ->  0/1/2..
    val type: String,               // eg : noun / verb/ adj
    val pronun: Pronunciation,
    val searchResultItemList: List<SearchResultItem>
)
