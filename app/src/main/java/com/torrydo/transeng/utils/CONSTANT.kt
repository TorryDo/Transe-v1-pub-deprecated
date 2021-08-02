package com.torrydo.transeng.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object CONSTANT {


    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val DEVICE_WIDTH = "dwidth"
    val DEVICE_HEIGHT = "dheight"

    val NOUN = "noun"
    val ADJ = "adjective"
    val VERB = "verb"

}