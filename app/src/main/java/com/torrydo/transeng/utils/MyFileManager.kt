package com.torrydo.transeng.utils

import android.util.Log
import com.torrydo.transeng.interfaces.RequestListener
import java.io.File
import java.io.FileOutputStream

class MyFileManager {

    private val TAG = "_TAG_MyFileManager"

    fun saveFile(
        file: File,
        byteArray: ByteArray,
        requestListener: RequestListener
    ) {

        val fos = FileOutputStream(file)
        fos.write(byteArray)
        fos.close()

        requestListener.request()
        Log.d(TAG, "file saved")

    }

}