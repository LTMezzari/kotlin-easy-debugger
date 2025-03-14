package mezzari.torres.lucas.easy_debugger.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.core.content.FileProvider
import mezzari.torres.lucas.easy_debugger.databinding.ActivityExceptionBinding
import java.io.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ExceptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExceptionBinding

    private var stackTrace: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExceptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Crash Logs"

        if (intent.hasExtra("exception")) {
            val exception: Throwable = intent.getSerializableExtra("exception") as Throwable
            val sw = StringWriter()
            exception.printStackTrace(PrintWriter(sw))
            stackTrace = sw.toString()

            binding.btnShare.isEnabled = !stackTrace.isNullOrEmpty()
            binding.tvException.text = stackTrace
        }

        binding.tvException.movementMethod = ScrollingMovementMethod()

        binding.btnShare.setOnClickListener {
            shareException()
        }
    }

    private fun createFile(): File? {
        return try {
            val name = SimpleDateFormat("yyyy-MM-dd_hh-mm-ss", Locale.getDefault()).format(Date())
            File(cacheDir, "$name.txt")
        } catch (e: Exception) {
            null
        }
    }

    private fun writeExeption(exception: String, file: File) {
        val fileWriter = FileWriter(file)
        fileWriter.use {
            it.write(exception)
            it.flush()
        }
    }

    private fun shareException() {
        stackTrace?.run {
            val file = createFile() ?: return
            writeExeption(this, file)

            startActivity(
                Intent.createChooser(
                    Intent(Intent.ACTION_SEND).apply {
                        type = "text/*"
                        putExtra(
                            Intent.EXTRA_STREAM,
                            FileProvider.getUriForFile(
                                this@ExceptionActivity,
                                "mezzari.torres.lucas.easy_debugger.library.provider",
                                file
                            )
                        )
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    },
                    "Sharing with"
                )
            )
        }
    }
}
