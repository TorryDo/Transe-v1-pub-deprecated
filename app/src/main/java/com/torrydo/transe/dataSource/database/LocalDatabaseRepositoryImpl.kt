package com.torrydo.transe.dataSource.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.torrydo.transe.dataSource.database.local.VocabDao
import com.torrydo.transe.dataSource.database.local.models.Vocab

class LocalDatabaseRepositoryImpl(
    private val vocabDao: VocabDao
) : LocalDatabaseRepository {

    private val TAG = "_TAG_DatabaseRepository"

    override suspend fun insert(vocab: Vocab) {
        try {
            vocabDao.insertVocab(vocab)
        } catch (e: Exception) {
            Log.e(TAG, "message: \n ${e.message}")
        }
    }

    override suspend fun insertAllVocab(vocabList: Array<Vocab>) {
        Log.e(TAG, "Not yet implement 'insertAllVocab'")
    }

    override suspend fun update(vocab: Vocab) {
        try {
            vocabDao.updateVocab(vocab)
        } catch (e: Exception) {
            Log.e(TAG, "message: \n ${e.message}")
        }
    }

    override suspend fun delete(vocab: Vocab) {
        try {
            vocabDao.delete(vocab)
        } catch (e: Exception) {
            Log.e(TAG, "message: \n ${e.message}")
        }
    }

    override suspend fun deleteAll() {
        try {
            vocabDao.deleteAll()
        } catch (e: Exception) {
            Log.e(TAG, "message: \n ${e.message}")
        }
    }


    override suspend fun loadVocabByKeyword(keyWord: String): Vocab? {
        var vocab: Vocab? = null

        val myJob = Thread { vocab = vocabDao.loadVocabByKeyword(keyWord) }
        myJob.start()
        myJob.join()

        return vocab
    }

    override suspend fun getAll(): List<Vocab> {
        return vocabDao.getAll()
    }

    override suspend fun getAllLiveData(): LiveData<List<Vocab>> {
        return vocabDao.getAllLiveData()
    }

}