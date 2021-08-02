package com.torrydo.transeng.dataSource.data.eng.pronunciation

import android.content.Context
import com.torrydo.transeng.dataSource.data.eng.pronunciation.models.Pronunciation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PronunciationHelper(
    private val context: Context
) {

    fun playAudio(
        pronunciation: Pronunciation
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            PronunciationAudioImpl(
                context,
                pronunciation.url
            ).play(pronunciation.text)
        }
    }

}