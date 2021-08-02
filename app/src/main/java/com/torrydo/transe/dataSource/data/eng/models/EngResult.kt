package com.torrydo.transe.dataSource.data.eng.models

import com.torrydo.transe.dataSource.data.eng.pronunciation.models.Pronunciation

data class EngResult (
    val type : String,
    var pronunciation: Pronunciation,
    val innerEngResultList : List<InnerEngResult>
)