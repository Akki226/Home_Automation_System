package appsync.ai.kotlintemplate.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import appsync.ai.kotlintemplate.Modules.Admin
import appsync.ai.kotlintemplate.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.teamup.app_sync.AppSyncPleaseWait
import com.teamup.app_sync.AppSyncToast
import kotlinx.android.synthetic.main.activity_main_door.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MainDoor: AppCompatActivity() {

    lateinit var appContext: Context
    var is_on: Boolean = true
    val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_door)
        appContext = this
        Admin.HandleToolBar(appContext, "Main Door", go_back_img, title_head_txt)

        Handle_clickers()

        Handle_firebase_connectivity()
    }

    private fun Handle_firebase_connectivity() {

        AppSyncPleaseWait.showDialog(appContext, "loading", true)


        val myRef = database.getReference("users").child(Admin.tinyDB?.getString("username") ?: "").child("rooms").child(Admin.tinyDB?.getString("roomno") ?: "").child("devices").child("maindoor")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot != null) {
                    is_on = snapshot.getValue().toString().equals("0")
                    Handle_lock_check()
                } else {
                    AppSyncToast.showToast(appContext, "No Data")
                }
                AppSyncPleaseWait.stopDialog(appContext)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }

    private fun Handle_clickers() {
        lock_img.setOnClickListener {
            var one_or_zero: Int = (if (is_on) 1 else 0)
            database.getReference("users").child(Admin.tinyDB?.getString("username") ?: "").child("rooms").child(Admin.tinyDB?.getString("roomno") ?: "").child("devices").child("maindoor").setValue(one_or_zero)
        }
    }

    private fun Handle_lock_check() {
        if (is_on) {
            lock_img.setImageResource(R.drawable.locked)
        } else {
            lock_img.setImageResource(R.drawable.unlocked)
        }
    }
}