package mezzari.torres.lucas.easy_debugger.navigation.view

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.databinding.ActivityNavigationStackBinding
import mezzari.torres.lucas.easy_debugger.generic.BaseActivity
import mezzari.torres.lucas.easy_debugger.navigation.ActivityNavigationModule
import mezzari.torres.lucas.easy_debugger.navigation.model.ActivityNavigationStack
import mezzari.torres.lucas.easy_debugger.navigation.view.adapter.NavigationStackAdapter

/**
 * @author Lucas T. Mezzari
 * @since 17/03/25
 **/
internal class NavigationStackActivity : BaseActivity() {
    private lateinit var binding: ActivityNavigationStackBinding

    private val navigationStack: ActivityNavigationStack
        get() {
            return EasyDebugger.instance.getModuleByType<ActivityNavigationModule>()?.listener?.stack
                ?: ActivityNavigationStack()
        }

    private val adapter: NavigationStackAdapter by lazy {
        NavigationStackAdapter(this, navigationStack)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationStackBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.rvActivitiesStack.adapter = adapter
        binding.rvActivitiesStack.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}