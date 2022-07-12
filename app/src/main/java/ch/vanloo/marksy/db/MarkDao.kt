package ch.vanloo.marksy.db

import androidx.room.*
import ch.vanloo.marksy.entity.Mark
import ch.vanloo.marksy.entity.MarkWithSubject
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkDao {
    @Insert
    suspend fun insertAll(vararg marks: Mark): Array<Long>

    @Transaction
    @Query("SELECT * FROM marks")
    suspend fun getAll(): List<Mark>

    @Transaction
    @Query("SELECT * FROM marks")
    fun getAllFlow(): Flow<List<Mark>>

    @Transaction
    @Query("SELECT * FROM marks")
    suspend fun getAllWithSubject(): List<MarkWithSubject>

    @Transaction
    @Query("SELECT * FROM marks")
    fun getAllWithSubjectFlow(): Flow<List<MarkWithSubject>>

    @Transaction
    @Query("SELECT * FROM marks WHERE uid = :uid")
    suspend fun getById(uid: Long): Mark

    @Transaction
    @Query("SELECT * FROM marks WHERE uid = :uid")
    suspend fun getByIdWithSubject(uid: Long): MarkWithSubject

    @Transaction
    @Query("SELECT * FROM marks WHERE name LIKE :name")
    suspend fun findByName(name: String): List<Mark>

    @Transaction
    @Query("SELECT * FROM marks WHERE name LIKE :name")
    fun findByNameFlow(name: String): Flow<List<Mark>>

    @Transaction
    @Query("SELECT * FROM marks WHERE name LIKE :name")
    fun findByNameWithSubject(name: String): List<MarkWithSubject>

    @Transaction
    @Query("SELECT * FROM marks WHERE name LIKE :name")
    fun findByNameWithSubjectFlow(name: String): Flow<List<MarkWithSubject>>

    @Update
    suspend fun updateAll(vararg marks: Mark): Int

    @Delete
    suspend fun delete(mark: Mark): Int
}