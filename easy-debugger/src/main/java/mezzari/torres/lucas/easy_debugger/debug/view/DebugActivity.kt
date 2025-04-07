package mezzari.torres.lucas.easy_debugger.debug.view

import android.app.PictureInPictureParams
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.R
import mezzari.torres.lucas.easy_debugger.databinding.ActivityDebugBinding
import mezzari.torres.lucas.easy_debugger.debug.DebugModule
import mezzari.torres.lucas.easy_debugger.generic.BaseActivity
import mezzari.torres.lucas.easy_debugger.logger.AppLogger
import mezzari.torres.lucas.easy_debugger.service.FloatingWindowManager
import mezzari.torres.lucas.easy_debugger.debug.service.DebugActivityService
import mezzari.torres.lucas.easy_debugger.di.appLogger
import mezzari.torres.lucas.easy_debugger.di.floatingWindowManager
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule

/**
 * @author Lucas T. Mezzari
 * @since 22/03/25
 **/
internal class DebugActivity : BaseActivity(), MenuProvider {
    private lateinit var binding: ActivityDebugBinding

    private val windowManager: FloatingWindowManager by lazy {
        floatingWindowManager
    }

    private val logger: AppLogger by lazy {
        appLogger
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDebugBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        closeCurrentFloatingView()
        setupToolbar()
        setupFragment()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_debug_activity, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menuMinimize -> {
                minimizeContent()
                true
            }

            else -> false
        }
    }

    private fun setupToolbar() {
        val module = getModule() ?: return
        if (!module.canBeMinimized()) {
            return
        }
        addMenuProvider(this)
    }

    private fun setupFragment() {
        val fragment = getFragment() ?: return
        displayFragment(fragment)
    }

    private fun getDebugModule(): DebugModule? {
        return EasyDebugger.instance.getModuleByType<DebugModule>()
    }

    private fun getModule(): DebuggerModule? {
        val module = getDebugModule() ?: return null
        return module.currentActiveModule
    }

    private fun getFragment(): Fragment? {
        return getModule()?.onCreateDebugFragment()
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.apply {
            try {
                beginTransaction()
                    .replace(R.id.fcvContainer, fragment, fragment::class.java.name)
                    .commit()
            } catch (e: Exception) {
                logger.logError(e)
            }
        }
    }

    private fun closeCurrentFloatingView() {
        windowManager.destroyFloatingWindow(this, DebugActivityService::class)
    }

    private fun minimizeContent() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            enterPictureInPictureMode()
//            return
//        }
        windowManager.createFloatingWindow(this, DebugActivityService::class)
        finish()
    }
}