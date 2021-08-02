package com.torrydo.transe.dataSource.data.eng.pronunciation

import com.torrydo.transe.interfaces.RequestListener
import java.io.File

interface PronunciationAudio {

    fun downloadAndSavePronounce(file: File, requestListener: RequestListener)

    fun play(keyWord: String)

}