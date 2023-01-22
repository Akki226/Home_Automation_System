package appsync.ai.kotlintemplate.Activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import appsync.ai.kotlintemplate.Modules.Admin
import appsync.ai.kotlintemplate.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.teamup.app_sync.AppSyncPleaseWait
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class Login : AppCompatActivity() {

    lateinit var appContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        appContext = this

        Handle_clickers()
    }

    private fun Handle_clickers() {
        login_btn.setOnClickListener {
            var username = username_edt.text.toString().trim()
            var password = password_edt.text.toString().trim()
            Handle_login(username, password)
        }

        signup_txt.setOnClickListener {
            Admin.Handle_activity_opener(appContext, Signup::class.java)
        }
    }

    private fun Handle_login(username: String, password: String) {
        if (!username.isNullOrEmpty()) {
            if (!password.isNullOrEmpty()) {

                AppSyncPleaseWait.showDialog(appContext, "signing", true)

                Handle_firebase_login(username, password)


            } else {
                password_edt.error = "Enter password"
            }
        } else {
            username_edt.error = "Enter username"
        }

    }

    private fun Handle_firebase_login(username: String, password: String) {
        val database = Firebase.database
        val myRef = database.getReference("users").child(username)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                if (snapshot.value != null) {



                    var jsonObj: JSONObject
                    jsonObj = JSONObject(snapshot.value.toString())

                    val on_pass = jsonObj.getString("password").equals(password)


                    AppSyncPleaseWait.stopDialog(appContext)
                    if (on_pass) {
                        Admin.tinyDB?.putString("username", username)
                        Admin.tinyDB?.putBoolean("login", true)
                        myRef.removeEventListener(this)
                        finish()

                        Admin.Handle_activity_opener(appContext, Rooms::class.java)
                    } else {
                        password_edt.error = "Wrong password"
                    }
                } else {
                    username_edt.error = "No Such username"
                    AppSyncPleaseWait.stopDialog(appContext)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                username_edt.error = "No Such username"
                AppSyncPleaseWait.stopDialog(appContext)
            }

        })

    }
}