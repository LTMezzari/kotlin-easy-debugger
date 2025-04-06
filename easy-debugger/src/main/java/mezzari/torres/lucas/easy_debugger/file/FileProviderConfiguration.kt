package mezzari.torres.lucas.easy_debugger.file

import android.content.Context
import java.io.File

/**
 * @author Lucas T. Mezzari
 * @since 06/04/25
 **/
interface FileProviderConfiguration {

    val authority: String
    val printConfiguration: FileConfiguration
    val exceptionConfiguration: FileConfiguration
    val recordConfiguration: FileConfiguration

    data class FileConfiguration(
        private val path: String,
        private val source: FileSource,
        private val retention: RetentionType,
    ) {
        fun getParentDir(context: Context): File? {
            return when {
                source == FileSource.INTERNAL && retention == RetentionType.CACHE -> File(
                    context.cacheDir,
                    path
                )

                source == FileSource.EXTERNAL && retention == RetentionType.CACHE -> File(
                    context.externalCacheDir,
                    path
                )

                source == FileSource.INTERNAL && retention == RetentionType.FILE -> File(
                    context.filesDir,
                    path
                )

                source == FileSource.EXTERNAL && retention == RetentionType.FILE -> context.getExternalFilesDir(
                    path
                )

                else -> null
            }
        }
    }

    enum class FileSource {
        EXTERNAL,
        INTERNAL
    }

    enum class RetentionType {
        CACHE,
        FILE
    }
}