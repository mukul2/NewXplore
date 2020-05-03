package com.rafiq.newxplore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rafiq.newxplore.R
import com.rafiq.newxplore.adapter.TitlesAdapter
import com.rafiq.newxplore.api.Api
import com.rafiq.newxplore.api.ApiListener
import com.rafiq.newxplore.model.Category
import com.rafiq.newxplore.model.TitleModel
import com.rafiq.newxplore.utlis.MyAdManager
import com.rafiq.newxplore.utlis.MyFunctions
import kotlinx.android.synthetic.main.activity_main.ad_view_container
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.tv_title
import kotlinx.android.synthetic.main.activity_second_level_category.*

import java.lang.Exception

class MainActivity : AppCompatActivity() , ApiListener.TitleDownloadListener{
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onTitleDownloadSuccess(list: MutableList<TitleModel>?) {


        if (list != null) {

            var gson = Gson()
            MyFunctions().cacheData("title_manu_for_" +id, gson.toJson(list), baseContext)
            showRecycler(list)


           // recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        }else{


        }
    }

    private fun showRecycler(list: MutableList<TitleModel>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = TitlesAdapter(list)
        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }

    }

    override fun onTitleDownloadFailed(msg: String?) {
        try {
            var myFunctions = MyFunctions()
            var gson = Gson()
            val list = gson.fromJson(
                myFunctions.retriveData("title_manu_for_" +id, baseContext),
                object : TypeToken<MutableList<TitleModel>?>() {}.type
            ) as MutableList<TitleModel>

            showRecycler(list)
        }catch (e: Exception){
            Toast.makeText(baseContext,"Connect internet and refresh again",Toast.LENGTH_LONG).show()

        }

    }
    private lateinit var adView: AdView
    var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Xplore")
        Log.i("mkl","here 3")

        id = intent.getIntExtra("id",0)

        var name: String = intent.getStringExtra("name")
        tv_title.text = name

     Api.getInstance().getTitlesList(""+id,this)
        var myAdManager = MyAdManager()
        adView = AdView(this)

        myAdManager.loadBanner(adView,ad_view_container,windowManager,baseContext )


    }

    fun back(view: View) {
        onBackPressed()
    }
}
