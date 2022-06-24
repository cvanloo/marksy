package ch.vanloo.marksy.db

import androidx.room.*
import ch.vanloo.marksy.entity.Subject
import ch.vanloo.marksy.entity.SubjectWithMarks
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    @Insert
    suspend fun insertAll(vararg subjects: Subject): Array<Long>

    @Transaction
    @Query("SELECT * FROM subjects")
    fun getAll(): List<Subject>

    @Transaction
    @Query("SELECT * FROM subjects")
    fun getAllFlow(): Flow<List<Subject>>

    @Transaction
    @Query("SELECT * FROM subjects")
    fun getAllWithMarks(): Flow<List<SubjectWithMarks>>

    @Transaction
    @Query("SELECT * FROM subjects WHERE sid = :sid")
    suspend fun getById(sid: Long): Subject

    @Transaction
    @Query("SELECT * FROM subjects WHERE sid = :sid")
    suspend fun getByIdWithMarks(sid: Long): SubjectWithMarks

    @Transaction
    @Query("SELECT * FROM subjects WHERE name LIKE :name")
    fun findByName(name: String): List<Subject>

    @Transaction
    @Query("SELECT * FROM subjects WHERE name LIKE :name")
    fun findByNameFlow(name: String): Flow<List<Subject>>

    @Transaction
    @Query("SELECT * FROM subjects WHERE name LIKE :name")
    fun findByNameWithMarks(name: String): Flow<List<SubjectWithMarks>>

    @Update
    suspend fun updateAll(vararg subjects: Subject): Int

    @Delete
    suspend fun delete(subject: Subject): Int
}