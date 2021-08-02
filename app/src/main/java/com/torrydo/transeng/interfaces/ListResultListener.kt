package com.torrydo.transeng.interfaces

interface ListResultListener {

    fun <T> onSuccess(dataList: List<T>)

    fun onError(e: Exception){}

}