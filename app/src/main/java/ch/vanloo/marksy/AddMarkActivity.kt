package ch.vanloo.marksy

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import ch.vanloo.marksy.databinding.ActivityAddMarkBinding
import ch.vanloo.marksy.db.MarksDatabase
import ch.vanloo.marksy.entity.Mark
import ch.vanloo.marksy.entity.Semester
import ch.vanloo.marksy.entity.Subject
import kotlinx.coroutines.*
import java.text.DateFormat
import java.util.*

class AddMarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMarkBinding
    private lateinit var database: MarksDatabase
    private lateinit var scope: CoroutineScope

    private var date: Long = 0
    private var subject: Subject? = null
    private var currentSemester: Semester? = null

    private val datePickerDialogListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            date = calendar.timeInMillis
            val formattedDate = DateFormat.getDateInstance().format(date)
            binding.inputDate.setText(getString(R.string.formatted_date, formattedDate))
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = (application as MarksApplication).database
        scope = (application as MarksApplication).applicationScope

        val calendar = Calendar.getInstance()
        date = calendar.timeInMillis
        val formattedDate = DateFormat.getDateInstance().format(date)
        binding.inputDate.setText(getString(R.string.formatted_date, formattedDate))

        scope.launch {
            val semestersDao = database.semesterDao()
            currentSemester = semestersDao.getCurrentSemester()
        }

        binding.buttonCancel.setOnClickListener {
            finish()
        }

        binding.inputDate.setOnClickListener {
            DatePickerDialog(this,
                datePickerDialogListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val subjectDao = database.subjectsDao()
        scope.launch {
            val subjects = subjectDao.getAll()
            launch(Dispatchers.Main) {
                val adapter = ArrayAdapter(this@AddMarkActivity, R.layout.list_popup_item, subjects)
                binding.inputSubject.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, position, _ ->
                        subject = adapter.getItem(position)
                    }
                binding.inputSubject.setAdapter(adapter)
            }
        }

        binding.buttonCreate.setOnClickListener {
            // @TODO: Validate that all required fields are set
            val value = binding.inputMark.text.toString().toFloat()
            val weighting = binding.inputWeighting.text.toString().toFloat()
            val name = binding.inputName.text.toString()
            val date = Date(date)

            scope.launch {
                val sid = ensureCreatedSubjectID(subject)
                val mark = Mark(0, value, weighting, name, date, sid)
                val markDao = database.marksDao()
                markDao.insertAll(mark)
            }

            finish()
        }
    }

    private suspend fun ensureCreatedSubjectID(subject: Subject?): Long {
        val dao = database.subjectsDao()
        val name = binding.inputSubject.text.toString()

        if (subject != null) return subject.Sid

        val found = dao.findByName(name)
        if (found.isNotEmpty()) {
            return found[0].Sid
        }

        // @TODO: Possibly dangerous?
        val newSubject = Subject(0, name, currentSemester!!.Sid)
        return dao.insertAll(newSubject)[0]
    }
}