package com.torrydo.vocabsource

import com.torrydo.vocabsource.eng.EngSearch
import com.torrydo.vocabsource.eng.EngSearchImpl
import com.torrydo.vocabsource.eng.models.EngResult

class VocabSource {

    private var engSearch: EngSearch = EngSearchImpl()

    @Suppress("UNCHECKED_CAST")
    fun search(keyWord: String, res: (List<EngResult?>) -> Unit) {
        engSearch.getResult(keyWord, object : ResponseVocabList {
            override fun <T> onSuccess(list: List<T>) {
                if (!list.isNullOrEmpty() && list[0] is EngResult) {
                    val engList = list as List<EngResult>
                    res(engList)
                    println("search succeed")
                    return
                }
                println("search null")

            }

            override fun onError(exception: Exception) {
                res(emptyList())
                println("search ERROR")
            }

        })
    }

}