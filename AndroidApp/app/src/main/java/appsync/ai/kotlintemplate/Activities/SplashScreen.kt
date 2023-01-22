package appsync.ai.kotlintemplate.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import appsync.ai.kotlintemplate.Modules.Admin
import appsync.ai.kotlintemplate.R
import com.teamup.app_sync.AppSyncChangeNavigationColor
import com.teamup.app_sync.AppSyncFullScreenView

class SplashScreen : AppCompatActivity() {

    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppSyncFullScreenView.FullScreencall(this)
        AppSyncChangeNavigationColor.change(this)
        setContentView(R.layout.activity_splash_screen)
        context = this

        Admin.intializeLocalRoom(this)

        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            if (Admin.tinyDB?.getBoolean("login") == true) {
                /* main activity screen */
                startActivity(Intent(context, Rooms::class.java))
            } else {
                /* login screen */
                startActivity(Intent(context, Login::class.java))
            }

        }, 2000)


    }
}