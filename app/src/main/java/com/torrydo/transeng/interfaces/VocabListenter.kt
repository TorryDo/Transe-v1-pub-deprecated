package com.torrydo.transeng.interfaces

import com.torrydo.transeng.dataSource.database.local.models.Vocab
import com.torrydo.transeng.dataSource.data.eng.pronunciation.models.Pronunciation

interface VocabListenter {

    fun insert(vocab: Vocab){}

    fun delete(vocab: Vocab){}

    fun update(vocab: Vocab){}

    fun playPronunciation(pronunciation: Pronunciation){}

}