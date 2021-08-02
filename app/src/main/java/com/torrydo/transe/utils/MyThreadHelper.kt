package com.torrydo.transe.utils

import android.os.Handler
import android.os.Looper
import com.torrydo.transe.interfaces.RequestListener

class MyThreadHelper {

    fun startOnMainThread(requestListener: RequestListener) {
        Handler(Looper.getMainLooper()).post {
            requestListener.request()
        }
    }

}