package ch.vanloo.marksy.db

import androidx.annotation.WorkerThread
import ch.vanloo.marksy.entity.*
import kotlinx.coroutines.flow.Flow

class MarksRepository(
    private val markDao: MarkDao,
    private val subjectDao: SubjectDao,
    private val semesterDao: SemesterDao
) {
    val allMarks: Flow<List<Mark>> = markDao.getAll()
    val allMarksWithSubject: Flow<List<MarkWithSubject>> = markDao.getAllWithSubject()
    val allSubjects: Flow<List<Subject>> = subjectDao.getAllFlow()
    val allSubjectsWithMarks: Flow<List<SubjectWithMarks>> = subjectDao.getAllWithMarksFlow()

    @WorkerThread
    suspend fun insert(vararg marks: Mark) {
        markDao.insertAll(*marks)
    }

    @WorkerThread
    suspend fun insert(vararg subjects: Subject) {
        subjectDao.insertAll(*subjects)
    }

    @WorkerThread
    suspend fun insert(vararg semesters: Semester) {
        semesterDao.insertAll(*semesters)
    }
}