package ch.vanloo.marksy.entity

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import androidx.room.Entity
import androidx.room.PrimaryKey
import ch.vanloo.marksy.db.DateConverter
import ch.vanloo.marksy.db.NullableDateConverter
import java.util.*

@Entity(tableName = "semesters")
@TypeConverters(NullableDateConverter::class)
data class Semester(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "sid", index = true) val Sid: Long,
    @ColumnInfo(name = "name") val Name: String,
    @ColumnInfo(name = "description") val Description: String,
    @ColumnInfo(name = "begin") val begin: Date,
    @ColumnInfo(name = "end") val end: Date?,
)
