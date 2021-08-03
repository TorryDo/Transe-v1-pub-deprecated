package com.torrydo.transe.dataSource.translation.eng.pronunciation

import android.content.Context
import com.torrydo.transe.dataSource.translation.eng.pronunciation.models.Pronunciation
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