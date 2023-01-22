package appsync.ai.kotlintemplate.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import appsync.ai.kotlintemplate.Modules.Admin
import appsync.ai.kotlintemplate.R
import appsync.ai.kotlintemplate.Reqs.LightsReq
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.teamup.app_sync.AppSyncBackkgroundTint
import kotlinx.android.synthetic.main.single_heater.view.*
import kotlinx.android.synthetic.main.single_item.view.*
import kotlinx.android.synthetic.main.single_item.view.title_txt
import kotlinx.android.synthetic.main.single_lights.view.*
import kotlinx.android.synthetic.main.single_lights.view.light_img
import kotlinx.android.synthetic.main.single_lights.view.switch_img

class HeaterAdapter(val list: ArrayList<LightsReq>) : RecyclerView.Adapter<HeaterAdapter.ViewHolder>() {

    lateinit var appContext: Context

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        appContext = parent.context
        return ViewHolder(LayoutInflater.from(appContext).inflate(R.layout.single_heater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            title_txt.text = list[position].name

            if (list[position].status == 0) {
                switch_img.setImageResource(R.drawable.off_switch_ico)
                light_img.setImageResource(R.drawable.heater_off)
            } else {
                switch_img.setImageResource(R.drawable.on_ico)
                light_img.setImageResource(R.drawable.heater)

            }

            switch_img.setOnClickListener {
                val database = Firebase.database
                val myRef = database.getReference("users").child(Admin.tinyDB?.getString("username") ?: "").child("rooms").child(Admin.tinyDB?.getString("roomno") ?: "").child("devices").child("heaters")
                    .child((position + 1).toString())

                var obj = HashMap<String, Any>()
                var runtime1: Double

                if (list[position].status == 0) {
                    obj.put("status", 1)
                    obj.put("on_time", System.currentTimeMillis())
                } else {
                    obj.put("status", 0)
                    runtime1= (((System.currentTimeMillis()-list[position].on_time))/1000).toDouble()

                    var temp=list[position].runtime+runtime1
                    var powerConsumption=(1500/1000)*(temp/3600).toDouble()
                    var energyCost=52*powerConsumption.toDouble()
                    obj.put("energyCost",energyCost)
                    obj.put("powerConsumption",powerConsumption)
                    obj.put("runtime",temp)
                }


                myRef.updateChildren(obj)


            }
            showRuntimeH.setOnClickListener {
                var run=(list[position].power).toString()
                runtimeDH.setText(run)
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
