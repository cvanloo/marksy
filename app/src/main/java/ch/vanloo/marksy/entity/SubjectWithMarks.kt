package ch.vanloo.marksy.entity

import androidx.room.Embedded
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
    fun average(): Float {
        val weightedMarks = marks.map { it.Value * it.Weighting }.sum()
        val totalWeight = marks.map { it.Weighting }.sum()

        return weightedMarks / totalWeight
    }

    fun rounded(): Float {
        val avg = average()
        val base = floor(avg)

        return when (avg - base) {
            in 0.0..0.25 -> base
            in 0.26..0.75 -> base + 0.5f
            else -> base + 1
        }
    }
}
