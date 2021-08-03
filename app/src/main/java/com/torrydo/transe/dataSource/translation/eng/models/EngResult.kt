package com.torrydo.transe.dataSource.translation.eng.models

import com.torrydo.transe.dataSource.translation.eng.pronunciation.models.Pronunciation

data class EngResult (
    val type : String,
    var pronunciation: Pronunciation,
    val innerEngResultList : List<InnerEngResult>
)