package ch.vanloo.marksy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.vanloo.marksy.databinding.ActivityAddMarkBinding
import kotlinx.coroutines.launch
import java.util.Date

class AddMarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMarkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCancel.setOnClickListener {
            finish()
        }

        binding.buttonCreate.setOnClickListener {
            val value = binding.inputMark.text.toString().toFloat()
            val weighting = binding.inputWeighting.text.toString().toFloat()
            val name = binding.inputName.text.toString()
            val date = Date()

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