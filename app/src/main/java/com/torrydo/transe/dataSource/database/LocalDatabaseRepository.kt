package com.torrydo.transe.dataSource.database

import androidx.lifecycle.LiveData
import com.torrydo.transe.dataSource.database.local.models.Vocab

interface LocalDatabaseRepository {

    suspend fun insert(vocab: Vocab)
    suspend fun update(vocab: Vocab)
    suspend fun delete(vocab: Vocab)
    suspend fun get(keyWord: String, isReady: (vocab: Vocab?) -> Unit)
    suspend fun getAll(): LiveData<List<Vocab>>

}