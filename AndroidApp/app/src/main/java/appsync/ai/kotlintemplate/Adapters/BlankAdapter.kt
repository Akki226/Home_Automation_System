package appsync.ai.kotlintemplate.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import appsync.ai.kotlintemplate.R
import com.teamup.app_sync.AppSyncBackkgroundTint
import kotlinx.android.synthetic.main.single_item.view.*

class BlankAdapter(val list: ArrayList<String>) : RecyclerView.Adapter<BlankAdapter.ViewHolder>() {

    lateinit var appContext: Context

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        appContext = parent.context
        return ViewHolder(LayoutInflater.from(appContext).inflate(R.layout.activity_main, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            Handle_color(holder, position)

        }
    }

    private fun Handle_color(holder: RecyclerView.ViewHolder, position: Int) {
        if (position % 2 == 0) {
            AppSyncBackkgroundTint.setBackgroundTint(R.color.light_white, holder.itemView.liner, appContext)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}