/**
 * Little helpers to convert Kotlin types to Room types and back.
 */
package ch.vanloo.marksy.db

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long) = Date(timestamp)

    @TypeConverter
    fun fromDate(date: Date) = date.time
}

class NullableDateConverter {
    @TypeConverter
    fun toNullableDate(timestamp: Long?) = if (null != timestamp) Date(timestamp) else null

    @TypeConverter
    fun fromNullableDate(date: Date?) = date?.time
}