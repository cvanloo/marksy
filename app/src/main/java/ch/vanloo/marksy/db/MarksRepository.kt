package ch.vanloo.marksy.db

import androidx.annotation.WorkerThread
import ch.vanloo.marksy.entity.Mark
import ch.vanloo.marksy.entity.MarkWithSubject
import ch.vanloo.marksy.entity.Subject
import ch.vanloo.marksy.entity.SubjectWithMarks
import kotlinx.coroutines.flow.Flow

class MarksRepository(private val markDao: MarkDao, private val subjectDao: SubjectDao) {
    val allMarks: Flow<List<Mark>> = markDao.getAll()
    val allMarksWithSubject: Flow<List<MarkWithSubject>> = markDao.getAllWithSubject()
    val allSubjects: Flow<List<Subject>> = subjectDao.getAllFlow()
    val allSubjectsWithMarks: Flow<List<SubjectWithMarks>> = subjectDao.getAllWithMarks()

    @WorkerThread
    suspend fun insert(vararg marks: Mark) {
        markDao.insertAll(*marks)
    }

    @WorkerThread
    suspend fun insert(vararg subjects: Subject) {
        subjectDao.insertAll(*subjects)
    }
}