package appsync.ai.kotlintemplate.Activities

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import appsync.ai.kotlintemplate.Modules.Admin
import appsync.ai.kotlintemplate.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.teamup.app_sync.AppSyncToast
import kotlinx.android.synthetic.main.activity_login.password_edt
import kotlinx.android.synthetic.main.activity_login.username_edt
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.include_toolbar.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

class Signup : AppCompatActivity() {

    lateinit var appContext: Context

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        appContext = this
        Admin.HandleToolBar(appContext, "Signup", go_back_img, title_head_txt)

        Handle_clickers()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun Handle_clickers() {
        signup_btn.setOnClickListener {
            Handle_signup()
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun Handle_signup() {
        /* handle signup */
        var username = username_edt.text.toString().trim()
        var password = password_edt.text.toString().trim()
        var no_of_lights = no_lights_edt.text.toString().trim()
        var no_of_heaters = no_heaters_edt.text.toString().trim()
        var no_of_rooms = no_rooms_edt.text.toString().trim()


        /* input validations */
        if (!username.isNullOrEmpty()) {
            if (!password.isNullOrEmpty()) {
                if (!no_of_lights.isNullOrEmpty()) {
                    if (!no_of_heaters.isNullOrEmpty()) {
                        if (no_of_lights.toInt() < 10) {
                            if (no_of_heaters.toInt() < 10) {
                                Handle_siging_up(username, password, no_of_lights, no_of_heaters, no_of_rooms)
                            } else {
                                no_lights_edt.error = "should be less than 10"
                            }
                        } else {
                            no_lights_edt.error = "should be less than 10"
                        }
                    } else {
                        no_heaters_edt.error = "Enter Number of Heaters"
                    }
                } else {
                    no_lights_edt.error = "Enter Number of Lights"
                }
            } else {
                password_edt.error = "Enter Password"
            }
        } else {
            username_edt.error = "Enter Username"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun Handle_siging_up(username: String, password: String, noOfLights: String, noOfHeaters: String, noOfRooms: String) {
        /* set specific structure to firebase */
        val database = Firebase.database
        val myRef1 = database.getReference("users").child(username).child("password")
        myRef1.setValue(password)




        val simpleDate= SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val current=simpleDate.format(Date())
        val Date_timeref=database.getReference("users").child(username).child("Date_time")
        //Date_timeref.setValue(current)


        for (i in 1..noOfRooms.toInt()) {
            val myRef = database.getReference("users").child(username).child("rooms").child(""+i)

            /* create fields in firebase */

            val lights_ref = myRef.child("devices").child("lights")
            val heaters_ref = myRef.child("devices").child("heaters")
            val maindoor_ref = myRef.child("devices").child("maindoor")

            /* for lights */
            var all_lights_Obj = HashMap<String, Any>()
            for (i in 1..noOfLights.toInt()) {
                var inner_Obj = HashMap<String, Any>()
                inner_Obj.put("status", 0)
                inner_Obj.put("runtime", 0.0)
                inner_Obj.put("on_time", 0.0)
                inner_Obj.put("of_time", 0.0)
                inner_Obj.put("Wattage",50)
                inner_Obj.put("energyCost",0)
                inner_Obj.put("powerConsumption",0)
                all_lights_Obj.put("" + i, inner_Obj)
            }

            /* for heaters */
            var all_heaters_Obj = HashMap<String, Any>()
            for (i in 1..noOfHeaters.toInt()) {
                var inner_Obj = HashMap<String, Any>()
                inner_Obj.put("status", 0)
                inner_Obj.put("runtime", 0.0)
                inner_Obj.put("on_time", 0.0)
                inner_Obj.put("of_time", 0.0)
                inner_Obj.put("Wattage",1500)
                inner_Obj.put("energyCost",0)
                inner_Obj.put("powerConsumption",0)
                all_heaters_Obj.put("" + i, inner_Obj)
            }

            /* set values */
            lights_ref.setValue(all_lights_Obj)
            heaters_ref.setValue(all_heaters_Obj)
            /* for password */

            /* for maindoor */
            maindoor_ref.setValue(0)
        }

        AppSyncToast.showToast(appContext, "Signup Success, login to app")

        finish()
        Admin.OverrideNow(appContext)

    }
}
