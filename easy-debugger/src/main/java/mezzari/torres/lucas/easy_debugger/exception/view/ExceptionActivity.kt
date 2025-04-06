package mezzari.torres.lucas.easy_debugger.exception.view

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import mezzari.torres.lucas.core.di.fileManager
import mezzari.torres.lucas.core.file.FileManager
import mezzari.torres.lucas.easy_debugger.databinding.ActivityExceptionBinding
import mezzari.torres.lucas.core.generic.BaseActivity
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.exception.ExceptionModule
import mezzari.torres.lucas.easy_debugger.file.FileProviderConfiguration
import java.io.*
import java.lang.Exception

/**
 * @author Lucas T. Mezzari
 * @since 20/02/2020
 **/
internal class ExceptionActivity : BaseActivity() {

    private val mFileManager: FileManager by lazy { fileManager }

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
            val parentFile =
                getFileConfiguration()?.exceptionConfiguration?.getParentDir(this)
                    ?: throw Exception("Parent was not created")
            mFileManager.createFile(
                parentFile,
                "${mFileManager.getFileName()}.txt"
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun writeException(exception: String, file: File) {
        val fileWriter = FileWriter(file)
        fileWriter.use {
            it.write(exception)
            it.flush()
        }
    }

    private fun shareException() {
        stackTrace?.run {
            val file = createFile() ?: return
            writeException(this, file)

            val module = getExceptionModule() ?: return

            startActivity(
                Intent.createChooser(
                    Intent(Intent.ACTION_SEND).apply {
                        type = "text/*"
                        putExtra(
                            Intent.EXTRA_STREAM,
                            mFileManager.getUriForFile(
                                this@ExceptionActivity,
                                file,
                                module.fileConfiguration.authority
                            )
                        )
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    },
                    "Sharing with"
                )
            )
        }
    }

    private fun getExceptionModule(): ExceptionModule? {
        return EasyDebugger.instance.getModuleByType<ExceptionModule>()
    }

    private fun getFileConfiguration(): FileProviderConfiguration? {
        return getExceptionModule()?.fileConfiguration
    }
}
