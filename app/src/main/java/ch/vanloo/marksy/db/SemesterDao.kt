package ch.vanloo.marksy.db

import androidx.room.*
import ch.vanloo.marksy.entity.Semester
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface SemesterDao {
    @Insert
    suspend fun insertAll(vararg semesters: Semester): Array<Long>

    @Query("SELECT * FROM semesters ORDER BY sid DESC LIMIT 1")
    suspend fun getCurrentSemester(): Semester

    @Transaction
    @Query("SELECT * FROM semesters")
    fun getAll(): List<Semester>

    @Transaction
    @Query("SELECT * FROM semesters")
    fun getAllFlow(): Flow<List<Semester>>

    @Transaction
    @Query("SELECT * FROM semesters WHERE sid = :sid")
    suspend fun getById(sid: Long): Semester

    @Transaction
    @Query("SELECT * FROM semesters WHERE name LIKE :name")
    fun findByName(name: String): List<Semester>

    @Transaction
    @Query("SELECT * FROM semesters WHERE start_date BETWEEN :startDate AND :endDate")
    fun findByStartDateRange(startDate: Long, endDate: Long): List<Semester>

    @Update
    suspend fun updateAll(vararg semesters: Semester): Int

    @Delete
    suspend fun delete(semester: Semester): Int
}