package com.torrydo.transe.dataSource.data

import com.torrydo.transe.dataSource.data.eng.EngSearch
import com.torrydo.transe.interfaces.ListResultListener

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