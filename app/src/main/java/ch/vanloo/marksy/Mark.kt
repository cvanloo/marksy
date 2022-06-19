package ch.vanloo.marksy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "marks")
@TypeConverters(Converter::class)
data class Mark(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "uid") val Uid: Long,
    @ColumnInfo(name = "value") val Value: Float,
    @ColumnInfo(name = "weighting") val Weighting: Float,
    @ColumnInfo(name = "name") val Name: String,
    @ColumnInfo(name = "date") val Date: Date,
)
