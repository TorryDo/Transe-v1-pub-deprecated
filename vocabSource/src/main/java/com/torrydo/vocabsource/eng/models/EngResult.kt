package com.torrydo.vocabsource.eng.models

import com.torrydo.vocabsource.eng.pronunciation.models.Pronunciation

data class EngResult(
    val vocab: String="",
    val type: String,
    var pronunciation: Pronunciation,
    val innerEngResultList: List<InnerEngResult>
)