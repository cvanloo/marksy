package ch.vanloo.marksy

import android.app.Application
import ch.vanloo.marksy.db.MarksDatabase
import ch.vanloo.marksy.db.MarksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MarksApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { MarksDatabase.getDatabase(this, applicationScope) }
    val repository by lazy {
        MarksRepository(
            database.marksDao(),
            database.subjectsDao(),
            database.semestersDao()
        )
    }
}