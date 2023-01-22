package appsync.ai.kotlintemplate.Modules

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import appsync.ai.kotlintemplate.BuildConfig
import com.teamup.app_sync.R

object Admin {
    @JvmField
    val BASE_URL = "https://novoagri.in/Other/RappidTech/"

    @JvmField
    var tinyDB: TinyDB? = null
    var go_back = MutableLiveData<String>()
    var query_done = MutableLiveData<String>()
    var query_done2 = MutableLiveData<String>()
    var fragmentManager: FragmentManager? = null

    @JvmField
    var changelog = "changelog_" + BuildConfig.VERSION_CODE

    @JvmStatic
    val lineNumber: Int
        get() = Thread.currentThread().stackTrace[3].lineNumber

    @JvmStatic
    fun intializeLocalRoom(context: Context?) {
        tinyDB = TinyDB(context)
    }

    @JvmStatic
    fun OverrideNow(context: Context) {
        (context as Activity).overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    fun HandleToolBar(context: Context, title: String, backImg: ImageView, titleTxt: TextView) {
        titleTxt.text = "" + title
        backImg.setOnClickListener {
            (context as Activity).finish()
            OverrideNow(context)
        }
    }

    fun Handle_activity_opener(context: Context, opener_class: Class<*>?) {
        (context as Activity).startActivity(Intent(context, opener_class))
        OverrideNow(context)
    }

    //    Fragment manager will clear all stack and load only one single fragment
    fun loadFrag(f1: Fragment, fm: FragmentManager) {
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
        val ft = fm.beginTransaction()
        //  ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        ft.replace(R.id.container, f1, f1.tag)
        ft.commit()
    }

    //    Fragment manager can go back if stack is available
    fun loadFrag_add_back(f1: Fragment?, fm: FragmentManager) {
        val ft = fm.beginTransaction()
        //  ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        ft.replace(R.id.container, f1!!)
        ft.addToBackStack(null)
        ft.commit()
    }


    fun HandleFragToolbar(title: TextView, go_back_img: ImageView, titleTxt: String) {
        title.text = "" + titleTxt
        go_back_img.setOnClickListener { go_back.value = "ds" }
    }
}