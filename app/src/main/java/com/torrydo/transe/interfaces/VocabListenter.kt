package com.torrydo.transe.interfaces

import com.torrydo.transe.dataSource.database.local.models.Vocab
import com.torrydo.transe.dataSource.translation.eng.pronunciation.models.Pronunciation

interface VocabListenter {

    fun onTouch(){}

    fun insert(vocab: Vocab){}

    fun delete(vocab: Vocab){}

    fun update(vocab: Vocab){}

    fun playPronunciation(keyWord: String, pronunciation: Pronunciation){}

}