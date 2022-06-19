package ch.vanloo.marksy

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import ch.vanloo.marksy.databinding.ActivityAddMarkBinding
import ch.vanloo.marksy.db.MarksDatabase
import ch.vanloo.marksy.entity.Mark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class AddMarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMarkBinding
    private lateinit var database: MarksDatabase
    private lateinit var scope: CoroutineScope

    private var date: Long = 0

    private val datePickerDialogListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            this.date = calendar.timeInMillis
            binding.inputDate.setText(getString(R.string.formatted_date,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR)))
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = (application as MarksApplication).database
        scope = (application as MarksApplication).applicationScope

        val calendar = Calendar.getInstance()
        this.date = calendar.timeInMillis
        binding.inputDate.setText(getString(R.string.formatted_date,
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.YEAR)))

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
        val subjects = subjectDao.getAll()
        binding.inputSubject.setOnClickListener {
            val inflator = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dropdown = inflator.inflate(R.layout.dropdown_popup, null)
            val popupWindow = PopupWindow(dropdown,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                true)
            popupWindow.showAtLocation(dropdown, Gravity.CENTER, 0, 0)
        }

        binding.buttonCreate.setOnClickListener {
            // @TODO: Validate that all required fields are set
            val value = binding.inputMark.text.toString().toFloat()
            val weighting = binding.inputWeighting.text.toString().toFloat()
            val name = binding.inputName.text.toString()
            val date = Date(date)

            val mark = Mark(0, value, weighting, name, date, 1)

            val markDao = database.marksDao()
            scope.launch {
                markDao.insertAll(mark)
            }

            finish()
        }
    }
}