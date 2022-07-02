package ch.vanloo.marksy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.vanloo.marksy.databinding.ActivityCrashBinding

class CrashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler(this))
        binding = ActivityCrashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.errorText.text = intent.getStringExtra(EXTRA_ERROR)
    }

    companion object {
        const val EXTRA_ERROR = "ch.vanloo.marksy.activity_crash.EXTRA_ERROR"
    }
}