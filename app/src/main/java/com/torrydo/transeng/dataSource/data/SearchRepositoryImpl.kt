package com.torrydo.transeng.dataSource.data

import com.torrydo.transeng.dataSource.data.eng.EngSearch
import com.torrydo.transeng.interfaces.ListResultListener

class SearchRepositoryImpl(
    private val engSearch: EngSearch,
) : SearchRepository {

    override fun getEnglishSource(
        keyWord: String,
        listResultListener: ListResultListener
    ) {
        engSearch.getResult(keyWord, listResultListener)
    }

    override fun getViSource() {}


}