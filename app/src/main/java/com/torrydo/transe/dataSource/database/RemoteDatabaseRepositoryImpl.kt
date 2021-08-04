package com.torrydo.transe.dataSource.database

import com.torrydo.transe.dataSource.database.remote.RemoteDao
import com.torrydo.transe.dataSource.database.remote.RemoteVocab
import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.transe.interfaces.ResultListener

class RemoteDatabaseRepositoryImpl(
    private val remoteDao: RemoteDao
) : RemoteDatabaseRepository {


    override suspend fun setUserID(uid: String) = remoteDao.setUserID(uid)


    override suspend fun insert(remoteVocab: RemoteVocab, resultListener: ResultListener?) {
        remoteDao.insert(remoteVocab, resultListener)
    }

    override suspend fun insertAll(
        listRemoteVocab: List<RemoteVocab>,
        resultListener: ResultListener?
    ) = remoteDao.insertAll(listRemoteVocab, resultListener)


    override suspend fun update(remoteVocab: RemoteVocab, resultListener: ResultListener?) {
        remoteDao.update(remoteVocab, resultListener)
    }

    override suspend fun delete(remoteVocab: RemoteVocab, resultListener: ResultListener?) {
        remoteDao.delete(remoteVocab, resultListener)
    }

    override suspend fun getAll(listResultListener: ListResultListener) {
        remoteDao.getAllVocab(listResultListener)
    }
}