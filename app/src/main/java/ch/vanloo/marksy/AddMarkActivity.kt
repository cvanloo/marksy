package ch.vanloo.marksy

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.vanloo.marksy.databinding.ActivityAddMarkBinding
import ch.vanloo.marksy.entity.Mark
import kotlinx.coroutines.launch
import java.util.*

class AddMarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMarkBinding

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

        binding.buttonCreate.setOnClickListener {
            // @TODO: Validate that all required fields are set
            val value = binding.inputMark.text.toString().toFloat()
            val weighting = binding.inputWeighting.text.toString().toFloat()
            val name = binding.inputName.text.toString()
            val date = Date(date)

            val mark = Mark(0, value, weighting, name, date)

            val database = (application as MarksApplication).database
            val scope = (application as MarksApplication).applicationScope
            val dao = database.marksDao()
            scope.launch {
                dao.insertAll(mark)
            }

            finish()
        }
    }
}