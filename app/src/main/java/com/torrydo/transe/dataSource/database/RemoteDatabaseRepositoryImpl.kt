package com.torrydo.transe.dataSource.database

import com.torrydo.transe.dataSource.database.remote.RemoteDao
import com.torrydo.transe.dataSource.database.remote.models.BaseVocab
import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.transe.interfaces.ResultListener

class RemoteDatabaseRepositoryImpl(
    private val remoteDao: RemoteDao
) : RemoteDatabaseRepository {


    override suspend fun setUserID(uid: String) = remoteDao.setUserID(uid)


    override suspend fun insert(baseVocab: BaseVocab, resultListener: ResultListener?) {
        remoteDao.insert(baseVocab, resultListener)
    }

    override suspend fun insertAll(
        listBaseVocab: List<BaseVocab>,
        resultListener: ResultListener?
    ) = remoteDao.insertAll(listBaseVocab, resultListener)


    override suspend fun update(baseVocab: BaseVocab, resultListener: ResultListener?) {
        remoteDao.update(baseVocab, resultListener)
    }

    override suspend fun delete(baseVocab: BaseVocab, resultListener: ResultListener?) {
        remoteDao.delete(baseVocab, resultListener)
    }

    override suspend fun getAll(listResultListener: ListResultListener) {
        remoteDao.getAllVocab(listResultListener)
    }
}