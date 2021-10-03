package com.torrydo.transe.dataSource.database.local

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import com.torrydo.transe.utils.CONSTANT
import com.torrydo.vocabsource.eng.models.EngResult
import com.torrydo.vocabsource.vi.ViResult
import java.sql.Date

class RoomConverter {

    @TypeConverter
    fun fromListToJson(mList: List<EngResult>): String {
        val type = Types.newParameterizedType(List::class.java, EngResult::class.java)
        val adapter: JsonAdapter<List<EngResult>> = CONSTANT.moshi.adapter(type)
        return adapter.toJson(mList)
    }

    @TypeConverter
    fun fromJsonToList(mStr: String): List<EngResult>? {
        val type = Types.newParameterizedType(List::class.java, EngResult::class.java)
        return CONSTANT.moshi.adapter<List<EngResult>>(type).fromJson(mStr)
    }

    @TypeConverter
    fun fromViListToJson(mList: List<ViResult>?): String {
        return "[]"
    }

    @TypeConverter
    fun fromJsonToViList(mStr: String): List<ViResult>? {
        return null
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