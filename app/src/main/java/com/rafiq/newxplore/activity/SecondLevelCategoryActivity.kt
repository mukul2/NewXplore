package com.rafiq.newxplore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rafiq.newxplore.R
import com.rafiq.newxplore.adapter.MainCategoryAdapter
import com.rafiq.newxplore.adapter.SecondaryCategoryAdapter
import com.rafiq.newxplore.api.Api
import com.rafiq.newxplore.api.ApiListener
import com.rafiq.newxplore.model.Category
import com.rafiq.newxplore.utlis.MyAdManager
import com.rafiq.newxplore.utlis.MyFunctions
import kotlinx.android.synthetic.main.activity_blog_body.*
import kotlinx.android.synthetic.main.activity_main_category.*
import kotlinx.android.synthetic.main.activity_second_level_category.*
import kotlinx.android.synthetic.main.activity_second_level_category.ad_view_container
import kotlinx.android.synthetic.main.activity_second_level_category.tv_title
import java.lang.Exception

class SecondLevelCategoryActivity : AppCompatActivity(),
    ApiListener.ParentCategoryDownloadListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onParentCategoryDownloadSuccess(list: MutableList<Category>?) {
        if (list != null) {


            var gson = Gson()
            MyFunctions().cacheData("secondary_manu_for_" +id, gson.toJson(list), baseContext)
            showRecycler(list)





        } else {


        }
    }

    private fun showRecycler(list: MutableList<Category>) {
        viewManager = GridLayoutManager(this, 2)
        viewAdapter = SecondaryCategoryAdapter(list)
        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }

    }

    override fun onParentCategoryDownloadFailed(msg: String?) {

        try {
            var myFunctions = MyFunctions()
            var gson = Gson()
            val list = gson.fromJson(
                myFunctions.retriveData("secondary_manu_for_" +id, baseContext),
                object : TypeToken<MutableList<Category>?>() {}.type
            ) as MutableList<Category>
           
            showRecycler(list)
        }catch (e:Exception){
            Toast.makeText(baseContext,"Connect internet and refresh again",Toast.LENGTH_LONG).show()

        }


    }
    private lateinit var adView: AdView
    var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_level_category)

        id = intent.getIntExtra("id", 0)
       var name: String = intent.getStringExtra("name")
        tv_title.text = name

        //my_recycler_view
        Api.getInstance().get_all_secondary_category("" + id, this)

        var myAdManager = MyAdManager()
        adView = AdView(this)
        myAdManager.loadBanner(adView,ad_view_container,windowManager,baseContext )
    }

    fun back(view: View) {
        onBackPressed()
    }
}
