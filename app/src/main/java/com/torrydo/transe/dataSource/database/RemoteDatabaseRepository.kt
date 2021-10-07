package com.torrydo.transe.dataSource.database

import com.torrydo.transe.dataSource.database.remote.models.BaseVocab
import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.transe.interfaces.ResultListener

interface RemoteDatabaseRepository {

    suspend fun setUserID(uid: String)

    suspend fun insert(baseVocab: BaseVocab, resultListener: ResultListener?)

    suspend fun insertAll(listBaseVocab: List<BaseVocab>, resultListener: ResultListener?)

    suspend fun update(baseVocab: BaseVocab, resultListener: ResultListener?)

    suspend fun delete(baseVocab: BaseVocab, resultListener: ResultListener?)

    suspend fun getAll(listResultListener: ListResultListener)

}