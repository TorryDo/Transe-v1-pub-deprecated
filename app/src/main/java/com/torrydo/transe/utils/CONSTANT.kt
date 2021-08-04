package com.torrydo.transe.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object CONSTANT {


    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    const val viewModelModule = "viewModelModule"
    const val viewModelLocalDB = "viewModelLocalDbRepo"
    const val viewModelAuth = "viewModelAuth"
    const val viewModelRemoteDB = "viewModelRemoteDB"

    val DEVICE_WIDTH = "dwidth"
    val DEVICE_HEIGHT = "dheight"

    val NOUN = "noun"
    val ADJ = "adjective"
    val VERB = "verb"

}