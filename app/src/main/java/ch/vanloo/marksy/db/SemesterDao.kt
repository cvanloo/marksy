package ch.vanloo.marksy.db

import androidx.room.*
import ch.vanloo.marksy.entity.Semester
import ch.vanloo.marksy.entity.SemesterWithSubjects
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface SemesterDao {
    @Insert
    suspend fun insertAll(vararg semesters: Semester): Array<Long>

    @Transaction
    @Query("SELECT * FROM semesters ORDER BY `begin` DESC LIMIT 1")
    suspend fun getCurrent(): Semester

    @Transaction
    @Query("SELECT * FROM semesters ORDER BY `begin` DESC LIMIT 1")
    suspend fun getCurrentWithSubjects(): SemesterWithSubjects

    @Transaction
    @Query("SELECT * FROM semesters")
    suspend fun getAll(): List<Semester>

    @Transaction
    @Query("SELECT * FROM semesters")
    fun getAllFlow(): Flow<List<Semester>>

    @Transaction
    @Query("SELECT * FROM semesters")
    suspend fun getAllWithSubjects(): List<SemesterWithSubjects>

    @Transaction
    @Query("SELECT * FROM semesters")
    fun getAllWithSubjectsFlow(): List<SemesterWithSubjects>

    @Transaction
    @Query("SELECT * FROM semesters WHERE sid = :sid")
    suspend fun getById(sid: Long): Semester

    @Transaction
    @Query("SELECT * FROM semesters WHERE sid = :sid")
    suspend fun getByIdWithSubjects(sid: Long): SemesterWithSubjects

    @Transaction
    @Query("SELECT * FROM semesters WHERE name LIKE :name")
    suspend fun findByName(name: String): List<Semester>

    @Transaction
    @Query("SELECT * FROM semesters WHERE name LIKE :name")
    suspend fun findByNameWithSubjects(name: String): List<SemesterWithSubjects>

    @Transaction
    @Query("SELECT * FROM semesters WHERE `begin` BETWEEN :earliest AND :latest")
    suspend fun findByBeginsBetween(earliest: Long, latest: Long): List<Semester>

    @Transaction
    @Query("SELECT * FROM semesters WHERE `begin` BETWEEN :earliest AND :latest")
    suspend fun findByBeginsBetweenWithSubjects(earliest: Long, latest: Long): List<Semester>

    @Update
    suspend fun updateAll(vararg semesters: Semester): Int

    @Delete
    suspend fun delete(semester: Semester): Int
}