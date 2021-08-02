package com.torrydo.transe.dataSource.data.eng

import com.torrydo.transe.interfaces.ListResultListener

interface EngSearch {

    fun getResult(keyWord: String, listResultListener: ListResultListener)

}