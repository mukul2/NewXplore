package com.rafiq.newxplore.utlis

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.FrameLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_blog_body.*
import kotlinx.android.synthetic.main.activity_main_category.*

class MyAdManager {

    private lateinit var adView: AdView
    private lateinit var ad_view_container: FrameLayout
    private lateinit var windowManager: WindowManager
    private lateinit var contex: Context

    fun initBanner(context: Context, adView: AdView) {
        MobileAds.initialize(context, "ca-app-pub-3940256099942544~3347511713");
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

    }

     fun loadBanner(a: AdView,container: FrameLayout, w: WindowManager,c: Context) {
        // adView = AdView(contex)


        this.adView=a
        this.ad_view_container=container
        this.windowManager=w
        this.contex=c

         ad_view_container.addView(adView)

        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"

        adView.adSize = adSize

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this device."
        val adRequest = AdRequest
                .Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build()

        // Start loading the ad in the background.
        adView.loadAd(adRequest)
    }

    companion object {
        // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
        private val AD_UNIT_ID = "ca-app-pub-3940256099942544~3347511713"
    }
    // Determine the screen width (less decorations) to use for the ad width.
    // If the ad hasn't been laid out, default to the full screen width.
    private val adSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = ad_view_container.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(contex, adWidth)
        }

}