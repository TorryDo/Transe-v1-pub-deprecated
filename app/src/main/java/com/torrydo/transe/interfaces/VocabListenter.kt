package com.torrydo.transe.interfaces

import com.torrydo.transe.dataSource.database.local.models.Vocab

interface VocabListenter {

    fun onTouch(position: Int) {}

    fun insert(vocab: Vocab) {}

    fun delete(vocab: Vocab) {}

    fun update(vocab: Vocab) {}

    fun playPronunciation(keyWord: String, /*pronunciation: Pronunciation*/){}

}