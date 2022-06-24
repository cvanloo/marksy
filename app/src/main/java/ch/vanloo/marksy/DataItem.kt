package ch.vanloo.marksy

import ch.vanloo.marksy.entity.Mark
import ch.vanloo.marksy.entity.Subject

sealed class DataItem {
    abstract val id: Long

    data class MarkItem(val toMark: Mark) : DataItem() {
        override val id = toMark.Uid
    }

    data class SubjectHeader(val toSubject: Subject) : DataItem() {
        override val id = toSubject.Sid
    }
}