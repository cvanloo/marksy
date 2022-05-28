package ch.vanloo.marksy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.vanloo.marksy.databinding.ActivityAddMarkBinding

class AddMarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMarkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCancel.setOnClickListener {
            finish()
        }
    }
}