package appsync.ai.kotlintemplate.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import appsync.ai.kotlintemplate.Adapters.HeaterAdapter
import appsync.ai.kotlintemplate.Modules.Admin
import appsync.ai.kotlintemplate.R
import appsync.ai.kotlintemplate.Reqs.LightsReq
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.teamup.app_sync.AppSyncPleaseWait
import com.teamup.app_sync.AppSyncToast
import kotlinx.android.synthetic.main.activity_lights.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.json.JSONArray
import kotlin.collections.ArrayList

class Heaters() : AppCompatActivity() {

    lateinit var appContext: Context
    var list: ArrayList<LightsReq> = arrayListOf()
    lateinit var adapter: HeaterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lights)
        appContext = this

        Admin.HandleToolBar(appContext, "Heaters", go_back_img, title_head_txt)

        Handle_recycler()

        Handle_firebase_data_fetch()

    }

    private fun Handle_firebase_data_fetch() {
        AppSyncPleaseWait.showDialog(appContext, "loading", true)

        val database = Firebase.database
        val myRef = database.getReference("users").child(Admin.tinyDB?.getString("username") ?: "").child("rooms").child(Admin.tinyDB?.getString("roomno") ?: "").child("devices").child("heaters")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null)
                    Handle_adding_lights(snapshot.getValue().toString())
                else {
                    AppSyncToast.showToast(appContext, "No data found")
                    finish()
                    Admin.OverrideNow(appContext)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.wtf("Thor-" + javaClass.name + "-" + Admin.lineNumber, "Err: " + error)
            }

        });
    }

    private fun Handle_adding_lights(arrayList: String) {
//        Log.wtf("Thor-" + javaClass.name + "-" + Admin.lineNumber,"" + arrayList[1]::class.java.typeName)


        list.clear()

        var jsonArray: JSONArray
        jsonArray = JSONArray(arrayList)

        for (i in 1 until jsonArray.length()) {
            val status = jsonArray.getJSONObject(i)
            list.add(LightsReq("Heater " + i, status.getString("status").toInt(),status.getDouble("runtime"), status.getLong("on_time"),status.getDouble("powerConsumption")))
        }


        adapter.notifyDataSetChanged()

        AppSyncPleaseWait.stopDialog(appContext)

    }


    private fun Handle_recycler() {
        adapter = HeaterAdapter(list)
        recycler.layoutManager = GridLayoutManager(appContext, 2)
        recycler.adapter = adapter

    }
}