package com.itis.newsapp.data.db

import androidx.room.TypeConverter
import com.itis.newsapp.util.Const.TIME_STR_FORMAT
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.*
import java.util.Arrays.asList
import java.util.stream.Collectors

class DateConverter {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time?.toLong()
    }

}