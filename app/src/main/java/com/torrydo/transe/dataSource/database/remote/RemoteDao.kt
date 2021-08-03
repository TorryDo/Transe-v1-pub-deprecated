package com.torrydo.transe.dataSource.database.remote

import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.transe.interfaces.ResultListener

interface RemoteDao {

    fun getAllVocab(listResultListener: ListResultListener)

    fun insert(remoteVocab: RemoteVocab, resultListener: ResultListener?)

    fun insertAll(listRemoteVocab: List<RemoteVocab>, resultListener: ResultListener?)

    fun update(remoteVocab: RemoteVocab, resultListener: ResultListener?)

    fun delete(remoteVocab: RemoteVocab, resultListener: ResultListener?)

}