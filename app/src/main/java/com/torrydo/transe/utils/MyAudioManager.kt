package com.torrydo.transe.utils

import android.media.MediaPlayer
import android.util.Log

object MyAudioManager {

    private val TAG = "_TAG_MyAudioManager"

    fun playAudioFromPath(path: String) {

        synchronized(this) {
            try {
                val mediaPlayer = MediaPlayer()
                mediaPlayer.setDataSource(path)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: Exception) {
                Log.e(TAG, "unable to play audio from path, cause : \n ${e.cause}")
            }
        }

    }

}