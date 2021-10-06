package com.torrydo.transe.dataSource.database.local

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import com.torrydo.transe.adapter.vocabSource.model.SearchResult
import com.torrydo.transe.utils.CONSTANT
import java.sql.Date

class RoomConverter {

    @TypeConverter
    fun fromListToJson(inputList: List<SearchResult>): String {
        val type = Types.newParameterizedType(List::class.java, SearchResult::class.java)
        val adapter: JsonAdapter<List<SearchResult>> = CONSTANT.moshi.adapter(type)
        return adapter.toJson(inputList)
    }

    @TypeConverter
    fun fromJsonToList(inputString: String): List<SearchResult>? {
        val type = Types.newParameterizedType(List::class.java, SearchResult::class.java)
        return CONSTANT.moshi.adapter<List<SearchResult>>(type).fromJson(inputString)
    }

//    @TypeConverter
//    inline fun <reified T> fromListToJson(mList: List<T>): String {
//    val type = Types.newParameterizedType(List::class.java, T::class.java)
//    val adapter: JsonAdapter<List<T>> = CONSTANT.moshi.adapter(type)
//    return adapter.toJson(mList)
//}
//
//    @TypeConverter
//    inline fun <reified T> fromJsonToList(mStr: String): List<T>? {
//        val type = Types.newParameterizedType(List::class.java, T::class.java)
//        return CONSTANT.moshi.adapter<List<T>>(type).fromJson(mStr)
//    }

    @TypeConverter
    fun toDate(dateLong: Long): Date {
        return Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time;
    }

}