package com.rafiq.newxplore.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rafiq.newxplore.utlis.AsyncTask
import com.rafiq.newxplore.utlis.AsyncTask.returnBitmap
import com.rafiq.newxplore.utlis.NotificationHelper

class AppFirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        //   super.onMessageReceived(p0)

        Log.i("mkl", "not hit")
        if (p0 != null) {
            val link: String = p0.getData().get("url").toString()
            Log.i("mkl", link)
        }

        if (p0 != null) {
            val image: String = p0.getData().get("image").toString()
            val title: String = p0.getData().get("title").toString()
            val body: String = p0.getData().get("body").toString()
            val type: String = p0.getData().get("type").toString()
            val link: String = p0.getData().get("link").toString()

            if (type.equals("link_open")) {


                returnBitmap(image, AsyncTask.downloadListener { bitmap ->
                    Log.i("mkl", "size => " + bitmap.byteCount)
                    //  var notificationHelper:NotificationHelper(App)
                    //Toast.makeText(this, "helo", Toast.LENGTH_SHORT).show();
                    val notificationHelper = NotificationHelper(baseContext)
                    notificationHelper.LinkOpenarNotification2(title, body, link, bitmap, "d")

                })


            } else if (type.equals("notification_only")) {

                returnBitmap(image, AsyncTask.downloadListener { bitmap ->
                    Log.i("mkl", "size => " + bitmap.byteCount)
                    //  var notificationHelper:NotificationHelper(App)
                    //Toast.makeText(this, "helo", Toast.LENGTH_SHORT).show();
                    val notificationHelper = NotificationHelper(baseContext)
                    notificationHelper.createBigImageNotification(title, body, "chat", bitmap, "d")

                })

            } else if (type.equals("app_promote")) {

                returnBitmap(image, AsyncTask.downloadListener { bitmap ->
                    Log.i("mkl", "size => " + bitmap.byteCount)
                    //  var notificationHelper:NotificationHelper(App)
                    //Toast.makeText(this, "helo", Toast.LENGTH_SHORT).show();
                    val notificationHelper = NotificationHelper(baseContext)
                    notificationHelper.OpenPLayStore(title, body, "chat", bitmap, "d")

                })

            }


        } else {
            Log.i("mkl", "Data null")
        }
        super.onMessageReceived(p0)


    }
}
