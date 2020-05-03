package com.rafiq.newxplore.activity

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rafiq.newxplore.adapter.MainCategoryAdapter
import com.rafiq.newxplore.api.Api
import com.rafiq.newxplore.api.ApiListener
import com.rafiq.newxplore.model.Category
import com.rafiq.newxplore.utlis.MyAdManager
import com.rafiq.newxplore.utlis.MyFunctions
import com.rafiq.newxplore.utlis.doForMe
import kotlinx.android.synthetic.main.activity_main_category.*
import android.content.Intent
import android.net.Uri
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.ads.AdView
import com.rafiq.newxplore.R
import kotlinx.android.synthetic.main.more_apps_dialog.*
import java.lang.Exception


class MainCategoryActivity : AppCompatActivity(), ApiListener.ParentCategoryDownloadListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onParentCategoryDownloadSuccess(list: MutableList<Category>?) {

        if (list != null) {
            Log.i("mkl", "size => " + list.size)
        } else {
            Log.i("mkl", "size =>  null")

        }
        if (list != null) {

            var gson = Gson()


            MyFunctions().cacheData("main_manu", gson.toJson(list), baseContext)
            showRecycler(list)


            // recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        } else {


        }
    }

    private fun showRecycler(list: MutableList<Category>) {
        viewManager = GridLayoutManager(this, 2)
        viewAdapter = MainCategoryAdapter(list)
        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }

    }

    override fun onParentCategoryDownloadFailed(msg: String?) {
        Toast.makeText(baseContext, "Api hit error,reading from local storage", Toast.LENGTH_LONG).show()
        try {
            var myFunctions = MyFunctions()
            var gson = Gson()
            val list = gson.fromJson(
                    myFunctions.retriveData("main_manu", baseContext),
                    object : TypeToken<MutableList<Category>?>() {}.type
            ) as MutableList<Category>
            //  Log.i("mkl", myFunctions.retriveData("main_manu", baseContext))
            // Log.i("mkl", "data size " + list.size)
            showRecycler(list)
        } catch (e: Exception) {

        }


    }


    private lateinit var adView: AdView
    private lateinit var dialog: Dialog
    var isShowing: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_category)
        //showMoreAppsDialog()
        //   drawer.addDrawerListener(toggle)
        customDrawerButton.drawerLayout = drawer
        customDrawerButton.drawerLayout.addDrawerListener(customDrawerButton)
        customDrawerButton.setOnClickListener { customDrawerButton.changeState() }


        var myFunctions = MyFunctions()
        if (myFunctions.isInternetAvailable()) {
            // Toast.makeText(baseContext,"has internet",Toast.LENGTH_LONG).show()
        } else {
            // Toast.makeText(baseContext,"no internet",Toast.LENGTH_LONG).show()

        }

        Api.getInstance().get_all_parent_category(this)

        var myAdManager = MyAdManager()
        adView = AdView(this)
        myAdManager.loadBanner(adView, ad_view_container, windowManager, baseContext)

        FirebaseMessaging.getInstance().subscribeToTopic("new_xplore")

        /*
          val item1 = SecondaryDrawerItem().withIdentifier(1).withName("Item 1")
          val item2 = SecondaryDrawerItem().withIdentifier(1).withName("Item 2")
          val item3 = SecondaryDrawerItem().withIdentifier(1).withName("Item 3")
          val item4 = SecondaryDrawerItem().withIdentifier(1).withName("Item 4")

           drawer = DrawerBuilder().withActivity(this).addDrawerItems(item1,item2,item3,item4).build()

         */


    }


    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (isShowing == false) {
                showMoreAppsDialog()
                isShowing = true
            } else {
                dialog.show()
                // super.onBackPressed()
              //  finishAffinity()
            }
        }

        //super.onBackPressed()
    }


    fun back(view: View) {
        if (isShowing == false) {
            showMoreAppsDialog()
            isShowing = true
        } else {
            onBackPressed()
        }
    }


    private fun showMoreAppsDialog() {
         dialog =doForMe.showDialog(this, com.rafiq.newxplore.R.layout.more_apps_dialog)
        var tv_yes: TextView = dialog.findViewById(R.id.tv_yes)
        var tv_dialog_cancel: TextView = dialog.findViewById(R.id.tv_dialog_cancel)
        tv_yes.setOnClickListener {
            finishAffinity()
        }
        tv_dialog_cancel . setOnClickListener {
            dialog.dismiss()
        }
//        tv_no . setOnClickListener {
//            dialog.dismiss()
//        }


        //one app start
        var app_one: LinearLayout = dialog.findViewById(com.rafiq.newxplore.R.id.app_one)
        app_one.setOnClickListener {
            val appPackageName = "com.mukul.finddoctor" // getPackageName() from Context or Activity object
            openThatSpecificAppInPlaystore(appPackageName)

        }
        //one app end
        //two starts
        var app_two: LinearLayout = dialog.findViewById(com.rafiq.newxplore.R.id.app_two)
        app_two.setOnClickListener {

            val appPackageName = "com.mukul.finddoctor" // getPackageName() from Context or Activity object
            openThatSpecificAppInPlaystore(appPackageName)


        }
        //two ends


    }

    private fun openThatSpecificAppInPlaystore(packageName: String) {
        val uri: Uri = Uri.parse("market://details?id=" + packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        // To count with Play market backstack, After pressing back button,
// to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + baseContext.getPackageName())))
        }

    }

    fun openPlayStore(view: View) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=XploreBDSolution")))
    }

    fun openFacebookpage(view: View) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com")))

    }

    fun openYoutubeChannel(view: View) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com")))

    }


}
