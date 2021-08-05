package com.torrydo.transe.dataSource.database.remote

import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.transe.interfaces.ResultListener

interface RemoteDao {

    fun setUserID(uid: String)

    fun getAllVocab(listResultListener: ListResultListener)

    fun insert(baseVocab: BaseVocab, resultListener: ResultListener?)

    fun insertAll(listBaseVocab: List<BaseVocab>, resultListener: ResultListener?)

    fun update(baseVocab: BaseVocab, resultListener: ResultListener?)

    fun delete(baseVocab: BaseVocab, resultListener: ResultListener?)

}