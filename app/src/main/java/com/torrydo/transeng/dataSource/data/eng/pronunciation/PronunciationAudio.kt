package com.torrydo.transeng.dataSource.data.eng.pronunciation

import com.torrydo.transeng.interfaces.RequestListener
import java.io.File

interface PronunciationAudio {

    fun downloadAndSavePronounce(file: File, requestListener: RequestListener)

    fun play(keyWord: String)

}