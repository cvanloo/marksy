package ch.vanloo.marksy

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import androidx.activity.result.contract.ActivityResultContracts
import ch.vanloo.marksy.databinding.ActivityExportBinding
import com.google.gson.Gson
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ExportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExportBinding

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                try {
                    it.data?.data?.also { uri ->
                        contentResolver.openFileDescriptor(uri, "w")?.use {
                            FileOutputStream(it.fileDescriptor).use { stream ->
                                runBlocking {
                                    val res = async {
                                        val dao = (application as MarksApplication).database.subjectsDao()
                                        val all = dao.getAllWithMarksFlow()
                                        Gson().toJson(all)
                                    }
                                    val str = res.await().toByteArray()
                                    stream.write(str)
                                }
                            }
                        }
                    }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonExportJson.setOnClickListener {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/json"
                putExtra(Intent.EXTRA_TITLE, "marksy-export-${Date().time}.json")
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, Environment.DIRECTORY_DOCUMENTS)
            }

            startForResult.launch(intent)
        }
    }

    companion object {
        const val CREATE_FILE = 1
    }
}