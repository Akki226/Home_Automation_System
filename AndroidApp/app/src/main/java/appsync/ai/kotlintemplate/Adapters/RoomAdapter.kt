package appsync.ai.kotlintemplate.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import appsync.ai.kotlintemplate.Activities.MainActivity
import appsync.ai.kotlintemplate.Modules.Admin
import appsync.ai.kotlintemplate.R
import appsync.ai.kotlintemplate.Reqs.LightsReq

import appsync.ai.kotlintemplate.Reqs.RoomsReq
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.teamup.app_sync.AppSyncBackkgroundTint
import kotlinx.android.synthetic.main.single_item.view.*
import kotlinx.android.synthetic.main.single_item.view.title_txt
import kotlinx.android.synthetic.main.single_lights.view.*
import kotlinx.android.synthetic.main.single_rooms.view.*

public class RoomAdapter(val list: ArrayList<RoomsReq>) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    lateinit var appContext: Context
    var rooms=0

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        appContext = parent.context
        return ViewHolder(LayoutInflater.from(appContext).inflate(R.layout.single_rooms, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            title_txt.text = list[position].name
            rooms_img.setOnClickListener {
                val database = Firebase.database
                //val myRef = database.getReference("users").child(Admin.tinyDB?.getString("username") ?: "").child("rooms")
                    //.child((position + 1).toString())
                rooms=position+1
                Admin.tinyDB?.putString("roomno", rooms.toString())
                Admin.Handle_activity_opener(appContext, MainActivity::class.java)






            }

//            Handle_color(holder, position)

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
