package ch.vanloo.marksy.db

import androidx.room.*
import ch.vanloo.marksy.entity.Subject
import ch.vanloo.marksy.entity.SubjectAndMarks
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    @Insert
    suspend fun insertAll(vararg subjects: Subject): Array<Long>

    @Transaction
    @Query("SELECT * FROM subjects")
    fun getAll(): Flow<List<Subject>>

    @Transaction
    @Query("SELECT * FROM subjects")
    fun getAllWithMarks(): Flow<List<SubjectAndMarks>>

    @Transaction
    @Query("SELECT * FROM subjects WHERE sid = :sid")
    suspend fun getById(sid: Long): Subject

    @Transaction
    @Query("SELECT * FROM subjects WHERE sid = :sid")
    suspend fun getByIdWithMarks(sid: Long): SubjectAndMarks

    @Transaction
    @Query("SELECT * FROM subjects WHERE name LIKE :name")
    fun findByName(name: String): Flow<List<Subject>>

    @Transaction
    @Query("SELECT * FROM subjects WHERE name LIKE :name")
    fun findByNameWithMarks(name: String): Flow<List<SubjectAndMarks>>

    @Update
    suspend fun updateAll(vararg subjects: Subject): Int

    @Delete
    suspend fun delete(subject: Subject): Int
}