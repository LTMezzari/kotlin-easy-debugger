package mezzari.torres.lucas.easy_debugger.file.manager

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @author Lucas T. Mezzari
 * @since 29/03/25
 **/
class FileManagerImpl: FileManager {

    override fun createFile(parent: File, path: String, fileName: String): File {
        val contentPath = File(parent, path)
        return createFile(contentPath, fileName)
    }

    override fun createFile(parent: File, fileName: String): File {
        return File(parent, fileName)
    }

    override fun getUriForFile(context: Context, file: File, authority: String): Uri {
        return FileProvider.getUriForFile(context, authority, file)
    }

    override fun getFileName(): String {
        return SimpleDateFormat("yyyy-MM-dd_hh-mm-ss", Locale.getDefault()).format(Date())
    }
}