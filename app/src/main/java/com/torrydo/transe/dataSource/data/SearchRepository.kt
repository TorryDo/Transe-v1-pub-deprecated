package com.torrydo.transe.dataSource.data

import com.torrydo.transe.interfaces.ListResultListener

interface SearchRepository {

    fun getEnglishSource(keyWord: String, listResultListener: ListResultListener)

    fun getViSource()  // not yet implemented

}