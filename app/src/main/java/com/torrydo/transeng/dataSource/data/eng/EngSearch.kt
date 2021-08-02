package com.torrydo.transeng.dataSource.data.eng

import com.torrydo.transeng.interfaces.ListResultListener

interface EngSearch {

    fun getResult(keyWord: String, listResultListener: ListResultListener)

}