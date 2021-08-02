package com.torrydo.transe.interfaces

import com.torrydo.transe.dataSource.database.local.models.Vocab
import com.torrydo.transe.dataSource.data.eng.pronunciation.models.Pronunciation

interface VocabListenter {

    fun insert(vocab: Vocab){}

    fun delete(vocab: Vocab){}

    fun update(vocab: Vocab){}

    fun playPronunciation(pronunciation: Pronunciation){}

}