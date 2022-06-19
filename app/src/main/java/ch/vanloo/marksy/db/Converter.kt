package ch.vanloo.marksy.db

import androidx.room.TypeConverter
import java.util.Date

/**
 * Helper to convert Kotlin types to Room types and back.
 */
class Converter {
    @TypeConverter
    fun toDate(timestamp: Long) = Date(timestamp)

    @TypeConverter
    fun fromDate(date: Date) = date.time
}