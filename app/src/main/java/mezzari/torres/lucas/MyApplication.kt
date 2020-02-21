package mezzari.torres.lucas

import android.app.Application
import android.content.Intent
import mezzari.torres.lucas.easy_debugger.source.EasyDebugger
import mezzari.torres.lucas.easy_debugger.source.Configuration
import mezzari.torres.lucas.easy_debugger.source.network.NetworkLoggerModule
import mezzari.torres.lucas.network.source.Network
import mezzari.torres.lucas.network.source.module.client.LogModule
import mezzari.torres.lucas.network.source.module.retrofit.GsonConverterModule
import mezzari.torres.lucas.view.LogActivity

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        EasyDebugger
            .builder(this)
            .setOnFloatingViewClickListener {
                startActivity(
                    Intent(this, LogActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
            .build()

        Network.initialize(
            retrofitLevelModules = arrayListOf(GsonConverterModule()),
            okHttpClientLevelModule = arrayListOf(LogModule(), NetworkLoggerModule())
        )
    }
}