package mezzari.torres.lucas.easy_debugger.navigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.databinding.FragmentNavigationStackBinding
import mezzari.torres.lucas.core.generic.BaseFragment
import mezzari.torres.lucas.easy_debugger.navigation.ActivityNavigationModule
import mezzari.torres.lucas.easy_debugger.navigation.model.ActivityNavigationStack
import mezzari.torres.lucas.easy_debugger.navigation.view.adapter.NavigationStackAdapter

/**
 * @author Lucas T. Mezzari
 * @since 17/03/25
 **/
internal class NavigationStackFragment : BaseFragment() {
    private lateinit var binding: FragmentNavigationStackBinding

    private val navigationStack: ActivityNavigationStack
        get() {
            return EasyDebugger.instance.getModuleByType<ActivityNavigationModule>()?.listener?.stack
                ?: ActivityNavigationStack()
        }

    private val adapter: NavigationStackAdapter by lazy {
        NavigationStackAdapter(requireContext(), navigationStack)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentNavigationStackBinding.inflate(inflater, container, false).let {
            binding = it
            return@let binding.root
        }
    }

    override fun onResume() {
        super.onResume()
        binding.rvActivitiesStack.adapter = adapter
        binding.rvActivitiesStack.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }
}