package com.rafiq.newxplore.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.rafiq.newxplore.BuildConfig
import com.rafiq.newxplore.R


class SplashActivity : AppCompatActivity() {
    lateinit var mAdView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

    }

    fun next(view: View) {
        startActivity(Intent(this, MainCategoryActivity::class.java))
        finishAffinity()
    }

    fun moreApps(view: View) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:XploreBDSolution")))
        } catch (anfe: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=Developer+Name+Here")))
        }
    }

    fun rateUs(view: View) {
        val uri: Uri = Uri.parse("market://details?id=" + baseContext.getPackageName())
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

    fun share(view: View) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    fun openFacebookpage(view: View) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com")))
    }

    fun openYoutubeChannel(view: View) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com")))

    }
}
