package ch.vanloo.marksy.entity

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import kotlin.math.floor

data class SubjectWithMarks(
    @Embedded
    val toSubject: Subject,
    @Relation(
        parentColumn = "sid",
        entityColumn = "subject_id",
    )
    val marks: List<Mark>,
) {
    @Ignore
    val average: Double

    init {
        val weightedMarks = marks.sumOf { (it.Value * it.Weighting).toDouble() }
        val totalWeight = marks.sumOf { it.Weighting.toDouble() }

        average = weightedMarks / totalWeight
    }

    @Ignore
    val rounded: Double

    init {
        val base = floor(average)

        rounded = when (average - base) {
            in 0.0..0.25 -> base
            in 0.26..0.75 -> base + 0.5f
            else -> base + 1
        }
    }
}
