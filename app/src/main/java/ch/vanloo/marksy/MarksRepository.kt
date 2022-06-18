package ch.vanloo.marksy

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class MarksRepository(private val markDao: MarkDao) {
    val allMarks: Flow<List<Mark>> = markDao.getAll()

    @WorkerThread
    suspend fun insert(vararg marks: Mark) {
        markDao.insertAll(*marks)
    }
}