package appsync.ai.kotlintemplate.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import appsync.ai.kotlintemplate.Adapters.RoomAdapter
import appsync.ai.kotlintemplate.Modules.Admin
import appsync.ai.kotlintemplate.R
import com.google.android.material.navigation.NavigationView
import com.teamup.app_sync.AppSyncInitialize
import appsync.ai.kotlintemplate.Activities.Rooms
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.single_item.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //    declare context here
    lateinit var appContext: Context



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppSyncInitialize.init(this)
        appContext = this

        /* for fragment manager */
        Admin.fragmentManager = supportFragmentManager

        /* handle naviagation drawere from here */
        HandleDrawerLayout()

        Handle_clickers()

        Handle_username_checker()

    }

    private fun Handle_username_checker() {
        var username: String = Admin.tinyDB?.getString("username") ?: ""
        if (username.isNullOrEmpty()) {
            Handle_logout()
        } else {
            email_txt.text = Admin.tinyDB?.getString("username")
        }
    }

    private fun Handle_clickers() {
        lights_card.setOnClickListener {
            Admin.Handle_activity_opener(appContext, Lights::class.java)
        }
        heaters_card.setOnClickListener {
            Admin.Handle_activity_opener(appContext, Heaters::class.java)
        }
        main_door_card.setOnClickListener {
            Admin.Handle_activity_opener(appContext, MainDoor::class.java)
        }
    }

    private fun HandleDrawerLayout() {
        nav_view.setNavigationItemSelectedListener(this)
        menu_img.setOnClickListener {
            if (!drawer_layout.isDrawerOpen(GravityCompat.START))
                drawer_layout.openDrawer(GravityCompat.START)
            else
                drawer_layout.closeDrawer(GravityCompat.END)
        }

        var header = nav_view.getHeaderView(0)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        /* navigation item selection code here */
        var id = item.itemId
        if (id == R.id.nav_logout) {
            Handle_logout()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun Handle_logout() {
        /* logout code here */
        Admin.tinyDB?.clear()
        finishAffinity()
        startActivity(Intent(appContext, SplashScreen::class.java))
        Admin.OverrideNow(appContext)
    }
}