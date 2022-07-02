package ch.vanloo.marksy.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "subjects",
    indices = [Index(value = ["name"], unique = true)],
    foreignKeys = [ForeignKey(entity = Semester::class,
        parentColumns = ["sid"],
        childColumns = ["semester_id"],
        onDelete = CASCADE)])
data class Subject(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "sid") val Sid: Long,
    @ColumnInfo(name = "name") val Name: String,
    @ColumnInfo(name = "semester_id") val Semester: Long,
) {
    override fun toString(): String {
        return Name
    }
}
