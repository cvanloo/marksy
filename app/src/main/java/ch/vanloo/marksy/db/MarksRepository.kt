package ch.vanloo.marksy.db

import androidx.annotation.WorkerThread
import ch.vanloo.marksy.entity.Mark
import ch.vanloo.marksy.entity.Subject
import ch.vanloo.marksy.entity.SubjectAndMarks
import kotlinx.coroutines.flow.Flow

class MarksRepository(private val markDao: MarkDao, private val subjectDao: SubjectDao) {
    val allMarks: Flow<List<Mark>> = markDao.getAll()
    val allSubjects: Flow<List<SubjectAndMarks>> = subjectDao.getAll()

    @WorkerThread
    suspend fun insert(vararg marks: Mark) {
        markDao.insertAll(*marks)
    }

    @WorkerThread
    suspend fun insert(vararg subjects: Subject) {
        subjectDao.insertAll(*subjects)
    }
}