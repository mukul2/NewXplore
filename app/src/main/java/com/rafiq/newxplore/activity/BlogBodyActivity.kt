package com.rafiq.newxplore.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.gson.Gson

import com.rafiq.newxplore.Data.myFunctions
import com.rafiq.newxplore.Data.myFunctions.FileDownlaodListener
import com.rafiq.newxplore.R
import com.rafiq.newxplore.api.Api
import com.rafiq.newxplore.api.ApiListener
import com.rafiq.newxplore.model.BlogBodyModel
import com.rafiq.newxplore.utlis.MyAdManager
import com.rafiq.newxplore.utlis.MyFunctions
import com.rafiq.newxplore.utlis.Utils
import kotlinx.android.synthetic.main.activity_blog_body.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.FileOutputStream


class BlogBodyActivity : AppCompatActivity(), ApiListener.BlogBodyDownloadListener ,myFunctions.FileDownlaodListener{
    override fun onBlogBodyDownloadSuccess(data: BlogBodyModel?) {
        Log.i("apr","dload success")
        if (data != null) {

            Log.i("apr","not null")


            // webview.loadUrl(data.body)
            var m:MyFunctions= MyFunctions()
            if (data.post_type.equals("pdf")){
               // Toast.makeText(baseContext,"Show pdf only",Toast.LENGTH_LONG).show()

                Log.i("apr","pdf")

               var myFunctions=myFunctions()
                pdfView.visibility=View.VISIBLE
                webview.visibility=View.GONE
                myFunctions.askPermission(data.body,this,this)




            }else  if (data.post_type.equals("html")){
                Log.i("apr","html")

                webview.loadHtml(data.body)
                Toast.makeText(baseContext,data.body,Toast.LENGTH_LONG).show()
                pdfView.visibility=View.GONE
                webview.visibility=View.VISIBLE

                var m:MyFunctions= MyFunctions()
                m.cacheData("DATA_"+id, data.body, baseContext)
                m.cacheData("TYPE_"+id, "TYPE_HTML", baseContext)
            }
            else if (data.post_type.equals("link_post")){
                pdfView.visibility=View.GONE
                webview.visibility=View.VISIBLE
                Log.i("apr","else")
                webview.loadUrl(data.body)

                Thread(Runnable {
                    val doc: Document = Jsoup.connect(data.body).get()

                    runOnUiThread(Runnable {
                        //webview.loadHtml(doc.html())
                        // writeFileOnInternalStorage(baseContext,"x.html",doc.html())
                        m.cacheData(data.body, doc.html(), baseContext)

                       // MyFunctions().cacheData("blog_body_link_for_" + id, doc.html(), baseContext)
                        var m:MyFunctions= MyFunctions()
                        m.cacheData("DATA_"+id, doc.html(), baseContext)
                        m.cacheData("TYPE_"+id, "TYPE_LINK", baseContext)

                    })

                    //  Log.i("mkl", doc.html())

                }).start()

            }


        } else {
            //  tv_body.text="No blog body or error occured"
            Log.i("apr"," null")

        }
    }


    override fun onBlogBodyDownloadFailed(msg: String?) {
        Log.i("apr"," failes")
        var myFunctions = MyFunctions()
        var type = myFunctions.retriveData("TYPE_" + id, baseContext)
        var data = myFunctions.retriveData("DATA_" + id, baseContext)

        if(type.equals("TYPE_PDF")){
            pdfView.visibility=View.VISIBLE
            webview.visibility=View.GONE
            val file = File(data)
            pdfView.fromFile(file).load()

        }else if(type.equals("TYPE_LINK")){

            pdfView.visibility=View.GONE
            webview.visibility=View.VISIBLE

            webview.loadHtml(data)

        }else if(type.equals("TYPE_HTML")){

            pdfView.visibility=View.GONE
            webview.visibility=View.VISIBLE

            webview.loadHtml(data)

        }

        /*

        try {
            var myFunctions = MyFunctions()
            var gson = Gson()


            webview.loadHtml( myFunctions.retriveData("blog_body_link_for_" +id, baseContext))
        }catch (e: Exception){
            Toast.makeText(baseContext,"Connect internet and refresh again",Toast.LENGTH_LONG).show()

        }

         */

    }

    private lateinit var mInterstitialAd: InterstitialAd
    private val filename = "SampleFile.txt"
    private val filepath = "MyFileStorage"
    var myExternalFile: File? = null

    var id: Int = 0
    private lateinit var adView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.rafiq.newxplore.R.layout.activity_blog_body)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())


        id= intent.getIntExtra("id", 0)
        var name: String = intent.getStringExtra("name")
        tv_title.text = name

        Log.i("apr","going to d "+id)
        Api.getInstance().getBlogBody("" + id, this)



//temp







        //addvertisement
        var myAdManager = MyAdManager()
        adView = AdView(this)

        myAdManager.loadBanner(adView,ad_view_container,windowManager,baseContext )


        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                //    mInterstitialAd.show()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        }

    }


    private class MyBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onBackPressed() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {

            super.onBackPressed()
        }
    }

    fun back(view: View) {

        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
            onBackPressed()
        }

    }

    override fun onDownloaded(uri: String?) {
        Toast.makeText(baseContext,"file downloaded "+uri,Toast.LENGTH_LONG).show()
        //pdfView.fromUri(Uri.parse("/storage/emulated/0/Android/data/com.rafiq.newxplore/files/myPdf.pdf"))
        val file = (File(uri))
        Log.i("apr", "file size " +file.totalSpace)
        Log.i("apr", "file name " +file.name)
        Log.i("apr", "file length " +file.length())
       // pdfView.fromFile(File("Android/data/com.rafiq.newxplore/files/myPdf.pdf"))
        pdfView.fromFile(file).load()
        pdfView.visibility=View.VISIBLE
        webview.visibility=View.GONE
        var m:MyFunctions= MyFunctions()
        m.cacheData("DATA_"+id, uri.toString(), baseContext)
        m.cacheData("TYPE_"+id, "TYPE_PDF", baseContext)
    }


}

private fun FileOutputStream.write(html: String?) {


}
