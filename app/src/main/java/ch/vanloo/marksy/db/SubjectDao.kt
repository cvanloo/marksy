package ch.vanloo.marksy.db

import androidx.room.*
import ch.vanloo.marksy.entity.SubjectAndMarks
import ch.vanloo.marksy.entity.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    @Insert
    suspend fun insertAll(vararg subjects: Subject): Array<Long>

    @Transaction
    @Query("SELECT * FROM subjects")
    fun getAll(): Flow<List<SubjectAndMarks>>

    @Transaction
    @Query("SELECT * FROM subjects WHERE sid = :sid")
    suspend fun getById(sid: Long): SubjectAndMarks

    @Transaction
    @Query("SELECT * FROM subjects WHERE name LIKE :name")
    fun findByName(name: String): Flow<List<SubjectAndMarks>>

    @Update
    suspend fun updateAll(vararg subjects: Subject): Int

    @Delete
    suspend fun delete(subject: Subject): Int
}