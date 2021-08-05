package com.torrydo.transe.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object CONSTANT {

    var KeyWord_Holder = ""

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()


    const val DEVICE_WIDTH = "dwidth"
    const val DEVICE_HEIGHT = "dheight"

    const val NOUN = "noun"
    const val ADJ = "adjective"
    const val VERB = "verb"


    // --------------------- App ------------------------
    const val appModule = "appModule"
    const val appSearchRepo = "appSearchRepo"
    const val appLocalDB = "appLocalDbRepo"
    // ------------------- Service ----------------------
    const val serviceModule = "serviceModule"
    const val serviceWindowManager = "serviceWindowManager"
    const val serviceSearchRepo = "serviceSearchRepo"
    const val serviceLocalDB = "serviceLocalDB"
    // ------------------- activity ---------------------
    const val activityModule = "activityModule"
    const val activityPopupMenuHelper = "activityPopMenuHelper"
    const val activityLocalDB = "activityLocalDbRepo"
    const val activityPronunciation = "activityPronunciation"
    // ------------------- fragment ---------------------
    const val fragmentModule = "fragmentModule"
    const val fragmentAuth = "fragmentAuth"
    // ------------------- viewModel --------------------
    const val viewModelModule = "viewModelModule"
    const val viewModelSearchRepo = "viewModelSearchRepo"
    const val viewModelLocalDB = "viewModelLocalDbRepo"
    const val viewModelAuth = "viewModelAuth"
    const val viewModelRemoteDB = "viewModelRemoteDB"

}