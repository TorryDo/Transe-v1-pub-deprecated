package com.torrydo.transeng.dataSource.data

import com.torrydo.transeng.interfaces.ListResultListener

interface SearchRepository {

    fun getEnglishSource(keyWord: String, listResultListener: ListResultListener)

    fun getViSource()  // not yet implemented

}