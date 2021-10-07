package com.torrydo.transe.dataSource.database

import androidx.lifecycle.LiveData
import com.torrydo.transe.dataSource.database.local.models.Vocab

interface LocalDatabaseRepository {

    suspend fun insert(vocab: Vocab)

    @Deprecated("not yet implement")
    suspend fun insertAllVocab(vocabList: Array<Vocab>)

    suspend fun update(vocab: Vocab)
    suspend fun delete(vocab: Vocab)
    suspend fun deleteAll()
    suspend fun loadVocabByKeyword(keyWord: String) : Vocab?
    suspend fun getAll(): List<Vocab>

    suspend fun getAllLiveData(): LiveData<List<Vocab>>

}