package mezzari.torres.lucas

import android.app.Application
import mezzari.torres.lucas.easy_debugger.source.EasyDebugger
import mezzari.torres.lucas.easy_debugger_network.NetworkLoggerModule
import mezzari.torres.lucas.network.source.Network
import mezzari.torres.lucas.network.source.module.client.LogModule
import mezzari.torres.lucas.network.source.module.retrofit.GsonConverterModule

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        EasyDebugger
            .builder(this)
            .setFloatingViewEnable(false)
            .build()

        Network.initialize(
            retrofitLevelModules = arrayListOf(GsonConverterModule()),
            okHttpClientLevelModule = arrayListOf(LogModule(),
                NetworkLoggerModule()
            )
        )
    }
}