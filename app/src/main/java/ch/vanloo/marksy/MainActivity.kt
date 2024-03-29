package ch.vanloo.marksy

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import ch.vanloo.marksy.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler(this))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener { _ ->
            val intent = Intent(this, AddMarkActivity::class.java)
            startActivity(intent)
        }
    }
}