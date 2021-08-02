package com.torrydo.transe.utils

import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.net.URL

class MyDownloader {

    private val TAG = "_TAG_MyDownloader"

    @OptIn(DelicateCoroutinesApi::class)
    fun download(
        urlStr: String,
        isReadied: (byteArray: ByteArray) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val bos = ByteArrayOutputStream()
                val mUrl = URL(urlStr)
                val inputStream = mUrl.openStream()
                val byteArray = ByteArray(1024 * 4)

                while (true) {
                    val n = inputStream.read(byteArray)
                    if (n <= 0) {
                        break
                    }
                    bos.write(byteArray, 0, n)
                }

                inputStream.close()

                isReadied(bos.toByteArray())

                bos.close()

            } catch (e: Exception) {
                Log.e(TAG, "can't save file")
            }
        }
    }

}