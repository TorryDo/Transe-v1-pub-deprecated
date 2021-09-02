package com.torrydo.transe.dataSource.translation

import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.transe.interfaces.ResultListener

interface SearchRepository {

    fun getEnglishSource(keyWord: String, listResultListener: ListResultListener)

    fun getImageList(keyWord: String, resultListener: ResultListener)

    @Deprecated("not yet implemented")
    fun getViSource()

}