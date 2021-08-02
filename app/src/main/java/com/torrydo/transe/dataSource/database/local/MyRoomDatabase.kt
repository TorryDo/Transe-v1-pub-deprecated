package com.torrydo.transe.dataSource.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.torrydo.transe.dataSource.database.local.models.Vocab

@Database(entities = [Vocab::class], version = 1)
@TypeConverters(RoomConverter::class)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract fun vocabDao(): VocabDao

    companion object {
        const val ROOM_NAME = "vocabroom"
        const val VOCAB_TABLE_NAME = "vocabtable"

        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getMyRoomDatabase(context: Context): MyRoomDatabase {

            var temp = INSTANCE
            if (temp != null) {
                return temp
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyRoomDatabase::class.java,
                    ROOM_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }

}