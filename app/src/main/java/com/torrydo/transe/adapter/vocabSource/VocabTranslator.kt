package com.torrydo.transe.adapter.vocabSource

import com.torrydo.transe.interfaces.ListResultListener

interface VocabTranslator {

    fun search(keyWord: String, listResultListener: ListResultListener)

}