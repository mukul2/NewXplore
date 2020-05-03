package com.rafiq.newxplore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.rafiq.newxplore.R
import kotlinx.android.synthetic.main.activity_blog_body.*

class LinkOpenarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_link_openar)
        //link
        val link: String = intent.getStringExtra("link")
        webview.loadUrl(link)
    }

    fun back(view: View) {
        startActivity(Intent(this,MainCategoryActivity::class.java))
    }

    override fun onBackPressed() {
        startActivity(Intent(this,MainCategoryActivity::class.java))
    }
}
