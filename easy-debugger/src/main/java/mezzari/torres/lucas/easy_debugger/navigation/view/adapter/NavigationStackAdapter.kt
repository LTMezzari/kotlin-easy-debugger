package mezzari.torres.lucas.easy_debugger.navigation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.easy_debugger.databinding.RowActivityEntryBinding
import mezzari.torres.lucas.easy_debugger.navigation.model.ActivityNavigationStack

/**
 * @author Lucas T. Mezzari
 * @since 17/03/25
 **/
class NavigationStackAdapter(
    context: Context,
    private val navigationStack: ActivityNavigationStack
) :
    RecyclerView.Adapter<NavigationStackAdapter.NavigationStackViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationStackViewHolder {
        return NavigationStackViewHolder(RowActivityEntryBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return navigationStack.activities.size
    }

    override fun onBindViewHolder(holder: NavigationStackViewHolder, position: Int) {
        val activity = navigationStack.activities[position]
        holder.binding.tvActivityName.text = activity.name
        holder.binding.tvActivityState.text = activity.state.name
        holder.binding.cbReferenceActive.isSelected = activity.isReferenceActive
        holder.binding.cbReferenceActive.isChecked = activity.isReferenceActive
    }

    class NavigationStackViewHolder(val binding: RowActivityEntryBinding) :
        RecyclerView.ViewHolder(binding.root)
}