package ch.vanloo.marksy.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.vanloo.marksy.entity.Mark
import ch.vanloo.marksy.entity.Semester
import ch.vanloo.marksy.entity.Subject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

// `exportSchema` false to avoid build errors. For migrations, consider setting this to true (default).
@Database(entities = [Mark::class, Subject::class, Semester::class],
    version = 2,
    exportSchema = true)
abstract class MarksDatabase : RoomDatabase() {
    abstract fun marksDao(): MarkDao
    abstract fun subjectsDao(): SubjectDao
    abstract fun semesterDao(): SemesterDao

    private class MarksDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val semestersDao = database.semesterDao()
                    val sid = semestersDao.insertAll(
                        Semester(0, "FR22", "Fruehlingssemester 2022", Date(), null)
                    )[0]

                    val subjectsDao = database.subjectsDao()
                    val ids = subjectsDao.insertAll(
                        Subject(0, "MAS", sid),
                        Subject(0, "NPS", sid),
                        Subject(0, "DEG", sid),
                        Subject(0, "ENG", sid),
                        Subject(0, "IDAF", sid),
                    )

                    val marksDao = database.marksDao()
                    marksDao.insertAll(
                        Mark(0, 6f, 2.5f, "Test 6", Date(), ids[0]),
                        Mark(0, 5.5f, 1f, "Test 5.5", Date(), ids[0]),
                        Mark(0, 5f, 1f, "Test 5", Date(), ids[1]),
                        Mark(0, 4.5f, 1f, "Test 4.5", Date(), ids[1]),
                        Mark(0, 4f, 1f, "Test 4", Date(), ids[2]),
                        Mark(0, 3.5f, .25f, "Test 3.5", Date(), ids[2]),
                        Mark(0, 3f, .25f, "Test 3", Date(), ids[3]),
                        Mark(0, 2.5f, .33f, "Test 2.5", Date(), ids[3]),
                        Mark(0, 2f, .33f, "Test 2", Date(), ids[4]),
                        Mark(0, 1.5f, .33f, "Test 1.5", Date(), ids[4]),
                        Mark(0, 1f, .33f, "Test 1", Date(), ids[4]),
                    )
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MarksDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MarksDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarksDatabase::class.java,
                    "marksy"
                )
                    .addCallback(MarksDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // Return instance
                instance
            }
        }
    }
}