package com.torrydo.transeng.dataSource.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.torrydo.transeng.dataSource.database.local.VocabDao
import com.torrydo.transeng.dataSource.database.local.models.Vocab
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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


    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun get(keyWord: String, isReady: (vocab: Vocab?) -> Unit) {
        GlobalScope.launch {
            val myJob = async { vocabDao.loadVocabByKeyword(keyWord) }
            myJob.join()         // depressing :<, check this later
            isReady(myJob.await())
        }
    }

    override suspend fun getAll(): LiveData<List<Vocab>> {
        return vocabDao.getAll()
    }

}