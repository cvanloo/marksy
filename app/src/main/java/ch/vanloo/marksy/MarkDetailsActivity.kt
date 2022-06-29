package ch.vanloo.marksy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import ch.vanloo.marksy.databinding.ActivityMarkDetailsBinding
import ch.vanloo.marksy.db.MarkDao
import ch.vanloo.marksy.db.MarksDatabase
import ch.vanloo.marksy.db.SubjectDao
import ch.vanloo.marksy.entity.Mark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import kotlin.math.max

class MarkDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMarkDetailsBinding
    private lateinit var database: MarksDatabase
    private lateinit var scope: CoroutineScope
    private lateinit var markDao: MarkDao
    private lateinit var subjectDao: SubjectDao
    private lateinit var mark: Mark

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarkDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = (application as MarksApplication).database
        scope = (application as MarksApplication).applicationScope
        markDao = database.marksDao()
        subjectDao = database.subjectsDao()

        scope.launch {
            val uid = intent.getLongExtra(MARK_ID, 0)
            mark = markDao.getById(uid)

            launch(Dispatchers.Main) {
                binding.preview.markValue.text = mark.Value.toString()
                binding.preview.markWeighting.text = mark.Weighting.toString()
                binding.preview.markName.text = mark.Name

                val formattedDate = DateFormat.getDateInstance().format(mark.Date)
                binding.preview.markDate.text = getString(R.string.formatted_date, formattedDate)

                val color = ColorGradient(this@MarkDetailsActivity).calculate(mark.Value)
                binding.preview.markValue.setTextColor(color)
            }
        }

        binding.buttonDelete.setOnClickListener {
            deleteMark()
            finish()
        }
    }

    private fun deleteMark() {
        scope.launch {
            markDao.delete(mark)
            val subject = subjectDao.getByIdWithMarks(mark.Subject)
            val marksLeft = subject.marks.size
            if (marksLeft <= 0) {
                subjectDao.delete(subject.toSubject)
            }
        }
    }

    companion object {
        const val MARK_ID = "ch.vanloo.marksy.MARK_ID"
    }
}