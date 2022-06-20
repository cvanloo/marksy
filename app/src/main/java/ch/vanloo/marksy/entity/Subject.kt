package ch.vanloo.marksy.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "sid") val Sid: Long,
    @ColumnInfo(name = "name") val Name: String,
) {
    override fun toString(): String {
        return Name
    }
}
