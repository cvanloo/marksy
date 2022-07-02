/**
 * Helpers to convert Kotlin types to Room types and back.
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
    fun toDateNullable(timestamp: Long?): Date? {
        if (null != timestamp) {
            return Date(timestamp)
        }
        return null
    }

    @TypeConverter
    fun fromDateNullable(date: Date?): Long? {
        if (null != date) {
            return date.time
        }
        return null
    }
}