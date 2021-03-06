package com.torrydo.transe.dataSource.database.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.torrydo.transe.dataSource.database.local.MyRoomDatabase.Companion.VOCAB_TABLE_NAME
import com.torrydo.transe.dataSource.database.local.models.Vocab

@Dao
interface VocabDao {

    // ------------------------- BASIC CRUD --------------------------------

    @Query("SELECT * FROM $VOCAB_TABLE_NAME")
    fun getAll(): List<Vocab>

    @Query("SELECT * FROM $VOCAB_TABLE_NAME")
    fun getAllLiveData(): LiveData<List<Vocab>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVocab(vocab: Vocab)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllVocab(vararg vocab: Vocab)

    @Update
    fun updateVocab(vocab: Vocab)

    @Delete
    fun delete(vocab: Vocab)

    @Query("DELETE FROM $VOCAB_TABLE_NAME")
    fun deleteAll()

    // ----------------------------------------------------------------------

    @Query("SELECT * FROM $VOCAB_TABLE_NAME WHERE vocab LIKE :keyword limit 1")
    fun loadVocabByKeyword(keyword : String) : Vocab?

    @Query("SELECT * FROM $VOCAB_TABLE_NAME ORDER BY RANDOM() LIMIT 1")
    fun getRandomVocab() : Vocab

}

//    @Query("SELECT * FROM $VOCAB_TABLE_NAME WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): LiveData<List<Vocab>>