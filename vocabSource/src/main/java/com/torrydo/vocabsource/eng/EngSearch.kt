package com.torrydo.vocabsource.eng

import com.torrydo.vocabsource.ResponseVocabList

interface EngSearch {

    fun getResult(keyWord: String, responseVocabList: ResponseVocabList)

}