package mezzari.torres.lucas

import android.app.Application
import mezzari.torres.lucas.easy_debugger.EasyDebugger.Companion.setupDebugger
import mezzari.torres.lucas.easy_debugger.debug.setDebugModule
import mezzari.torres.lucas.easy_debugger.exception.setExceptionModule
import mezzari.torres.lucas.easy_debugger.logs.setLoggerModule
import mezzari.torres.lucas.easy_debugger.navigation.setActivityNavigationModule
import mezzari.torres.lucas.easy_debugger.print.setPrintModule
import mezzari.torres.lucas.easy_debugger.record.setScreenRecordModule
import mezzari.torres.lucas.easy_debugger.settings.setSettingsModule
import mezzari.torres.lucas.easy_debugger_network.network.NetworkLoggerModule
import mezzari.torres.lucas.easy_debugger_network.setNetworkModule
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
            setLogsEnabled(BuildConfig.DEBUG)
            setDebugModule()
            setActivityNavigationModule()
            setExceptionModule()
            setLoggerModule()
            setSettingsModule()
            setNetworkModule()
            setPrintModule()
            setScreenRecordModule()
            addModule(NetworkOptionsModule())
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