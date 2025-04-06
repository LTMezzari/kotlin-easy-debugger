package mezzari.torres.lucas.debugger

import mezzari.torres.lucas.BuildConfig
import mezzari.torres.lucas.easy_debugger.file.FileProviderConfiguration

/**
 * @author Lucas T. Mezzari
 * @since 06/04/25
 **/
class MyFileProviderConfiguration : FileProviderConfiguration {
    override val authority: String = BuildConfig.fileProviderAuthorities
    override val printConfiguration: FileProviderConfiguration.FileConfiguration =
        FileProviderConfiguration.FileConfiguration(
            "prints",
            FileProviderConfiguration.FileSource.EXTERNAL,
            FileProviderConfiguration.RetentionType.FILE
        )
    override val exceptionConfiguration: FileProviderConfiguration.FileConfiguration =
        FileProviderConfiguration.FileConfiguration(
            "records",
            FileProviderConfiguration.FileSource.EXTERNAL,
            FileProviderConfiguration.RetentionType.FILE
        )
    override val recordConfiguration: FileProviderConfiguration.FileConfiguration =
        FileProviderConfiguration.FileConfiguration(
            "craches",
            FileProviderConfiguration.FileSource.EXTERNAL,
            FileProviderConfiguration.RetentionType.CACHE
        )
}