package ch.vanloo.marksy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ch.vanloo.marksy.databinding.ActivityMarkDetailsBinding
import ch.vanloo.marksy.db.MarkDao
import ch.vanloo.marksy.db.MarksDatabase
import ch.vanloo.marksy.entity.Mark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

class MarkDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMarkDetailsBinding
    private lateinit var database: MarksDatabase
    private lateinit var scope: CoroutineScope
    private lateinit var dao: MarkDao
    private lateinit var mark: Mark

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarkDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = (application as MarksApplication).database
        scope = (application as MarksApplication).applicationScope
        dao = database.marksDao()

        scope.launch {
            val uid = intent.getLongExtra(MARK_ID, 0)
            mark = dao.getById(uid)

            binding.preview.markValue.text = mark.Value.toString()
            binding.preview.markWeighting.text = mark.Weighting.toString()
            binding.preview.markName.text = mark.Name

            val formattedDate = DateFormat.getDateInstance().format(mark.Date)
            binding.preview.markDate.text = getString(R.string.formatted_date, formattedDate)

            // @DUP: Coloring:2
            if (mark.Value == 6.0f) {
                binding.preview.markValue.setTextColor(getColor(R.color.mark_6_0))
            } else if (mark.Value >= 5.5f) {
                binding.preview.markValue.setTextColor(getColor(R.color.mark_5_5))
            } else if (mark.Value >= 5.0f) {
                binding.preview.markValue.setTextColor(getColor(R.color.mark_5_0))
            } else if (mark.Value >= 4.5f) {
                binding.preview.markValue.setTextColor(getColor(R.color.mark_4_5))
            } else if (mark.Value >= 4.0f) {
                binding.preview.markValue.setTextColor(getColor(R.color.mark_4_0))
            } else {
                binding.preview.markValue.setTextColor(getColor(R.color.mark_failing))
            }
        }

        binding.buttonDelete.setOnClickListener {
            deleteMark()
            finish()
        }
    }

    private fun deleteMark() {
        scope.launch {
            dao.delete(mark)
        }
    }

    companion object {
        const val MARK_ID = "ch.vanloo.marksy.MARK_ID"
    }
}