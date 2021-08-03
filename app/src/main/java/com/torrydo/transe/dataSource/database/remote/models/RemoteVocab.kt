package com.torrydo.transe.dataSource.database.remote

data class RemoteVocab (
    val keyWord: String,
    val remoteVocabProperties: RemoteVocabProperties
)

data class RemoteVocabProperties(
    val time: Long,
    val isFinished: Boolean
)