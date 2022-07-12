package ch.vanloo.marksy.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import ch.vanloo.marksy.db.Converter
import java.util.*

@Entity(
    tableName = "marks", foreignKeys = [ForeignKey(
        entity = Subject::class,
        parentColumns = ["sid"],
        childColumns = ["subject_id"],
        onDelete = CASCADE
    )]
)
@TypeConverters(Converter::class)
data class Mark(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "uid") val Uid: Long,
    @ColumnInfo(name = "value") val Value: Float,
    @ColumnInfo(name = "weighting") val Weighting: Float,
    @ColumnInfo(name = "name") val Name: String,
    @ColumnInfo(name = "date") val Date: Date,
    @ColumnInfo(name = "subject_id", index = true) val Subject: Long,
)
