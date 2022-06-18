package ch.vanloo.marksy

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkDao {
    @Insert
    suspend fun insertAll(vararg marks: Mark): Array<Long>

    @Query("SELECT * FROM marks")
    fun getAll(): Flow<List<Mark>>

    @Query("SELECT * FROM marks WHERE uid = :uid")
    fun getById(uid: Long): Mark

    @Query("SELECT * FROM marks WHERE name LIKE :name")
    fun findByName(name: String): Flow<List<Mark>>

    @Update
    suspend fun updateAll(vararg marks: Mark): Int

    @Delete
    suspend fun delete(mark: Mark): Int
}