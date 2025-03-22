package mezzari.torres.lucas

import android.app.Application
import mezzari.torres.lucas.easy_debugger.EasyDebugger.Companion.setupDebugger
import mezzari.torres.lucas.easy_debugger.print.PrintModule
import mezzari.torres.lucas.easy_debugger_network.NetworkModule
import mezzari.torres.lucas.easy_debugger_network.network.NetworkLoggerModule
import mezzari.torres.lucas.module.NetworkOptionsModule
import mezzari.torres.lucas.network.source.Network
import mezzari.torres.lucas.network.source.module.client.LogModule
import mezzari.torres.lucas.network.source.module.retrofit.GsonConverterModule

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        setupDebugger {
            setApplication(this@MyApplication)
            addModule(NetworkModule())
            addModule(NetworkOptionsModule())
            addModule(PrintModule())
        }

        Network.initialize(
            retrofitLevelModules = arrayListOf(GsonConverterModule()),
            okHttpClientLevelModule = arrayListOf(
                LogModule(),
                NetworkLoggerModule(),
            )
        )
    }
}