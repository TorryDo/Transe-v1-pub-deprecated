package com.torrydo.transe.interfaces

interface ListResultListener {

    fun <T> onSuccess(dataList: List<T>)

    fun onError(e: Exception){}

}