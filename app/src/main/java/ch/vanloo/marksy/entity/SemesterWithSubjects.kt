package ch.vanloo.marksy.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SemesterWithSubjects(
    @Embedded
    val toSemester: Semester,
    @Relation(
        parentColumn = "sid",
        entityColumn = "semester_id",
    )
    val subjects: List<Subject>
)
