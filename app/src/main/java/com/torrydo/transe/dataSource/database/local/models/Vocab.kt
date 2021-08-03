package com.torrydo.transe.dataSource.database.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.torrydo.transe.dataSource.translation.eng.models.EngResult
import com.torrydo.transe.dataSource.translation.vi.ViResult
import com.torrydo.transe.dataSource.database.local.MyRoomDatabase
import java.sql.Date

@Entity(tableName = MyRoomDatabase.VOCAB_TABLE_NAME)
data class Vocab(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "date") val time: Date,
    @ColumnInfo(name = "vocab") val vocab: String,
    @ColumnInfo(name = "contentEng") val contentEng: List<EngResult>,
    @ColumnInfo(name = "contentVi") val contentVi: List<ViResult>?

)
