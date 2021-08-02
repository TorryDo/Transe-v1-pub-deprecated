package com.torrydo.transeng.interfaces

interface ResultListener {

    fun <T> onSuccess(data: T)

    fun onError(e: Exception){}

}