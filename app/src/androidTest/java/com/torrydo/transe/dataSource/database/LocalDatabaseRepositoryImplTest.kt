package com.torrydo.transe.dataSource.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.torrydo.transe.dataSource.database.local.MyRoomDatabase
import com.torrydo.transe.dataSource.database.local.VocabDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class LocalDatabaseRepositoryImplTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var localDB: MyRoomDatabase
    private lateinit var dao: VocabDao

    @Before
    fun setup(){
        localDB = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MyRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = localDB.vocabDao()
    }

    fun teardown(){
        localDB.close()
    }

    fun loadVocabFromLocalDB_Test() = runBlockingTest {

        val result1 = dao.loadVocabByKeyword("add")
//        val result1 = dao.loadVocabByKeyword("add")
//        val result1 = dao.loadVocabByKeyword("add")
//        val result1 = dao.loadVocabByKeyword("add")
//        val result1 = dao.loadVocabByKeyword("add")


        Truth.assertThat(result1).isNotNull()
    }


}