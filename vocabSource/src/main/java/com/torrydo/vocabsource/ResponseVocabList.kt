package com.torrydo.vocabsource

import kotlin.Exception

interface ResponseVocabList {

    fun <T> onSuccess(list: List<T>?)

    fun onError(exception: Exception)

}