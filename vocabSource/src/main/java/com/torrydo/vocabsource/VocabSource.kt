package com.torrydo.vocabsource

import com.torrydo.vocabsource.eng.EngSearch
import com.torrydo.vocabsource.eng.EngSearchImpl
import com.torrydo.vocabsource.eng.models.EngResult

class VocabSource {

    private lateinit var engSearch: EngSearch

    private fun build(){
        engSearch = EngSearchImpl()
    }

    fun search(keyWord: String, res: (List<EngResult?>) -> Unit){
        build()
        engSearch.getResult(keyWord, object : ResponseVocabList{
            override fun <T> onSuccess(list: List<T>?) {
                list?.let {
                    if(it[0] == EngResult::class.java) res(list as List<EngResult>)
                }

            }

            override fun onError(exception: Exception) {
                res(emptyList())
            }

        })
    }

}