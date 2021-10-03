package com.torrydo.vocabsource.eng.pronunciation

class PronunciationAudioImpl(
    private val url: String,
) : PronunciationAudio {

    private val TAG = "_TAG_PronunciationAudioImpl"

    companion object {
        const val PRONUNCIATION_FOLDER = "Pronunciation"
    }

    override fun play(keyWord: String) {

        print("fake play vocab")

    }

}