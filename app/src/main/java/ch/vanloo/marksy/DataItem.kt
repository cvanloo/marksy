package ch.vanloo.marksy

import ch.vanloo.marksy.entity.Mark
import ch.vanloo.marksy.entity.SubjectWithMarks

sealed class DataItem {
    abstract val id: Long

    data class MarkItem(val toMark: Mark) : DataItem() {
        override val id = toMark.Uid
    }

    data class SubjectHeader(val toSubject: SubjectWithMarks) : DataItem() {
        override val id = toSubject.toSubject.Sid
    }
}