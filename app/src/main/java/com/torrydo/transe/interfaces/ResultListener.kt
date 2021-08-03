package com.torrydo.transe.interfaces

interface ResultListener {

    fun <T> onSuccess(data: T?){}

    fun onError(e: Exception){}

}