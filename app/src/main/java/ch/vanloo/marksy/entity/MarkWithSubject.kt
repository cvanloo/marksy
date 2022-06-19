package ch.vanloo.marksy.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MarkWithSubject(
    @Embedded
    val mark: Mark,
    @Relation(
        parentColumn = "subject_id",
        entityColumn = "sid",
    )
    val subject: Subject,
)
