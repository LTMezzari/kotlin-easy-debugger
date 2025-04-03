package mezzari.torres.lucas.core.file

import android.content.Context
import android.net.Uri
import mezzari.torres.lucas.core.di.fileProviderAuthorities
import java.io.File

/**
 * @author Lucas T. Mezzari
 * @since 29/03/25
 **/
interface FileManager {
    fun createFile(parent: File, fileName: String): File

    fun createFile(parent: File, path: String, fileName: String): File

    fun getUriForFile(context: Context, file: File, providerName: String = fileProviderAuthorities): Uri

    fun getFileName(): String
}