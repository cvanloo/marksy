package ch.vanloo.marksy.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SubjectWithMarks(
    @Embedded
    val toSubject: Subject,
    @Relation(
        parentColumn = "sid",
        entityColumn = "subject_id",
    )
    val marks: List<Mark>,
)
