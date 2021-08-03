package com.torrydo.transe.dataSource.translation.eng

import com.torrydo.transe.interfaces.ListResultListener

interface EngSearch {

    fun getResult(keyWord: String, listResultListener: ListResultListener)

}