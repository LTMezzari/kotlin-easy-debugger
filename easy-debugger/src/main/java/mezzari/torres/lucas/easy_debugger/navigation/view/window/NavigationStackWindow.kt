package mezzari.torres.lucas.easy_debugger.navigation.view.window

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.core.archive.listenerForObservableList
import mezzari.torres.lucas.core.model.ObservableList
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.databinding.LayoutMinimizedNavigationStackBinding
import mezzari.torres.lucas.easy_debugger.generics.MinimizedWindow
import mezzari.torres.lucas.easy_debugger.navigation.ActivityNavigationModule
import mezzari.torres.lucas.easy_debugger.navigation.model.ActivityNavigationStack
import mezzari.torres.lucas.easy_debugger.navigation.model.ActivityWrapper
import mezzari.torres.lucas.easy_debugger.navigation.view.adapter.NavigationStackAdapter

/**
 * @author Lucas T. Mezzari
 * @since 26/03/25
 **/
class NavigationStackWindow : MinimizedWindow() {

    private lateinit var binding: LayoutMinimizedNavigationStackBinding

    private val navigationStack: ActivityNavigationStack
        get() {
            return EasyDebugger.instance.getModuleByType<ActivityNavigationModule>()?.listener?.stack
                ?: ActivityNavigationStack()
        }

    private val adapter: NavigationStackAdapter by lazy {
        NavigationStackAdapter(context, navigationStack)
    }

    private val listener: ObservableList.Listener<ActivityWrapper> by lazy {
        adapter.listenerForObservableList()
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?): View {
        return LayoutMinimizedNavigationStackBinding.inflate(inflater, parent, false).let {
            binding = it
            return@let binding.root
        }
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        binding.rvActivitiesStack.adapter = adapter
        binding.rvActivitiesStack.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        navigationStack.activities.addListener(listener)
    }

    override fun onDestroy() {
        super.onDestroy()
        navigationStack.activities.removeListener(listener)
    }
}