package com.torrydo.transeng.utils

import android.os.Handler
import android.os.Looper
import com.torrydo.transeng.interfaces.RequestListener

class MyThreadHelper {

    fun startOnMainThread(requestListener: RequestListener) {
        Handler(Looper.getMainLooper()).post {
            requestListener.request()
        }
    }

}