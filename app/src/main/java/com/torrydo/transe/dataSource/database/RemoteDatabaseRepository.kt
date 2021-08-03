package com.torrydo.transe.dataSource.database

import com.torrydo.transe.dataSource.database.remote.RemoteVocab
import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.transe.interfaces.ResultListener

interface RemoteDatabaseRepository {

    suspend fun insert(remoteVocab: RemoteVocab, resultListener: ResultListener?)

    suspend fun insertAll(listRemoteVocab: List<RemoteVocab>, resultListener: ResultListener?)

    suspend fun update(remoteVocab: RemoteVocab, resultListener: ResultListener?)

    suspend fun delete(remoteVocab: RemoteVocab, resultListener: ResultListener?)

    suspend fun getAll(listResultListener: ListResultListener)

}