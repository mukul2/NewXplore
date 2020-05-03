package com.rafiq.newxplore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.rafiq.newxplore.R
import kotlinx.android.synthetic.main.activity_blog_body.*
import kotlinx.android.synthetic.main.activity_main_category.*

class AdaptiveBannerBaseActivity : AppCompatActivity() {
    private lateinit var adView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptive_banner_base)
      //  adView = AdView(this)
      //  ad_view_container.addView(adView)
     
    }

}
