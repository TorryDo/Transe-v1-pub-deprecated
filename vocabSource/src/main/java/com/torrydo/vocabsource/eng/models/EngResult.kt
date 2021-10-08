package com.torrydo.vocabsource.eng.models

data class EngResult(
    val vocab: String="",
    val type: String,
    var pronunciation: Pronunciation,
    val innerEngResultList: List<InnerEngResult>
)