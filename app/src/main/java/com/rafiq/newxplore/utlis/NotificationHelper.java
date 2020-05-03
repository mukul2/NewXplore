package com.rafiq.newxplore.utlis;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;


import com.rafiq.newxplore.R;
import com.rafiq.newxplore.activity.LinkOpenarActivity;
import com.rafiq.newxplore.activity.MainCategoryActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationHelper {
    SessionManager sessionManager;

    private Context mContext;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "100011";
    String userType;

    public NotificationHelper(Context context) {
        mContext = context;
        sessionManager = new SessionManager(context);
        userType = sessionManager.getUserType();

    }

    /**
     * Create and push the notification
     */
    public void createNotification(String title, String body, String intent, Bitmap image, String targetUserType) {
        /**Creates an explicit intent for an Activity in your app**/


        //userType.equals(SharedData.LEVEL_1) && userType.equals(SharedData.LEVEL_2) &&
        //!((userType.equals(SharedData.LEVEL_1) && intent.equals("job")) || (userType.equals(SharedData.LEVEL_2) && intent.equals("job")))
        Intent resultIntent = null;
        if (true) {

            String message = "";


            switch (intent) {
                case "chat": {
                    resultIntent = new Intent(mContext, MainCategoryActivity.class);
                    message = body;
                    title = title;
                    break;
                }
                case "appointment": {
                    if (targetUserType.equals("d")) {
                        resultIntent = new Intent(mContext, MainCategoryActivity.class);
                        message = body;
                        title = title;
                    } else {

                    }
                  /*  resultIntent = new Intent(mContext, PendingJobsByLevel1AdminActivity.class);
                    message = "Appointment Information";
                    title = "Appointment update";

                   */
                    break;
                }
                case "adminResponse": {
                    resultIntent = new Intent(mContext, MainCategoryActivity.class);
                    message = body;
                    title = title;
                    break;
                }
                default: {
                    resultIntent = new Intent(mContext, MainCategoryActivity.class);

                }
            }
            //  resultIntent = new Intent(mContext, SplashActivity.class);


            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.logo);
            if (image != null) {
                icon = image;
            }
            // newMsgNotification(mContext,title,message,image);


            mBuilder = new NotificationCompat.Builder(mContext);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(false)
                    .setLargeIcon(icon)
                    .setSmallIcon(R.drawable.logo)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentIntent(resultPendingIntent);

            mNotificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(intent, intent, importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                mBuilder.setChannelId(intent);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(0 /* Request Code */, mBuilder.build());


            //another noti
            PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), resultIntent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
            Notification n = new Notification.Builder(mContext)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.logo)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();


            n = new NotificationCompat.Builder(mContext, "1")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(icon)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(icon)
                            .bigLargeIcon(null))
                    .build();


            NotificationManager notificationManager =
                    (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);

        } else {

// use System.currentTimeMillis() to have a unique ID for the pending intent

        }
    }

    public void createBigImageNotification(String title, String body, String intent, Bitmap image, String targetUserType) {
        /**Creates an explicit intent for an Activity in your app**/


        //userType.equals(SharedData.LEVEL_1) && userType.equals(SharedData.LEVEL_2) &&
        //!((userType.equals(SharedData.LEVEL_1) && intent.equals("job")) || (userType.equals(SharedData.LEVEL_2) && intent.equals("job")))
        Intent resultIntent = null;
        if (true) {

            String message = "";


            switch (intent) {
                case "chat": {
                    resultIntent = new Intent(mContext, MainCategoryActivity.class);
                    message = body;
                    title = title;
                    break;
                }
                case "appointment": {
                    if (targetUserType.equals("d")) {
                        resultIntent = new Intent(mContext, MainCategoryActivity.class);
                        message = body;
                        title = title;
                    } else {

                    }
                  /*  resultIntent = new Intent(mContext, PendingJobsByLevel1AdminActivity.class);
                    message = "Appointment Information";
                    title = "Appointment update";

                   */
                    break;
                }
                case "adminResponse": {
                    resultIntent = new Intent(mContext, MainCategoryActivity.class);
                    message = body;
                    title = title;
                    break;
                }
                default: {
                    resultIntent = new Intent(mContext, MainCategoryActivity.class);

                }
            }
            //  resultIntent = new Intent(mContext, SplashActivity.class);


            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.logo);
            Bitmap logoBitmap=icon;
            if (image != null) {
                icon = image;
            }

            // newMsgNotification(mContext,title,message,image);


            mBuilder = new NotificationCompat.Builder(mContext);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentTitle("this hard code is working")
                    .setContentText(message)
                    .setAutoCancel(false)
                    .setLargeIcon(icon)
                    .setSmallIcon(R.drawable.logo)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentIntent(resultPendingIntent);

            Notification notification = new NotificationCompat.Builder(mContext, intent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(icon)
                    .setContentIntent(resultPendingIntent)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(icon)
                            .bigLargeIcon(null))
                    .build();



            mNotificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(intent, intent, importance);
                notificationChannel.enableLights(true);
               // notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                mBuilder.setChannelId(intent);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(0 /* Request Code */, notification);


            //another noti
            PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), resultIntent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
            Notification n = new Notification.Builder(mContext)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.logo)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();


            n = new NotificationCompat.Builder(mContext, "1")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(icon)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(icon)
                            .bigLargeIcon(null))
                    .build();


            NotificationManager notificationManager =
                    (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

         //   notificationManager.notify(0, n);

        } else {

// use System.currentTimeMillis() to have a unique ID for the pending intent

        }
    }
    public void LinkOpenarNotification(String title, String body, String intent, Bitmap image, String targetUserType) {
        /**Creates an explicit intent for an Activity in your app**/


        //userType.equals(SharedData.LEVEL_1) && userType.equals(SharedData.LEVEL_2) &&
        //!((userType.equals(SharedData.LEVEL_1) && intent.equals("job")) || (userType.equals(SharedData.LEVEL_2) && intent.equals("job")))
        Intent resultIntent = null;
        if (true) {

            String message = "";



                    resultIntent = new Intent(mContext, LinkOpenarActivity.class);
                    resultIntent.putExtra("link",intent);
                    message = body;
                    title = title;

                }



            //  resultIntent = new Intent(mContext, SplashActivity.class);


            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.logo);
            Bitmap logoBitmap=icon;
            if (image != null) {
                icon = image;
            }

            // newMsgNotification(mContext,title,message,image);


            mBuilder = new NotificationCompat.Builder(mContext);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentTitle("this hard code is working")
                    .setContentText("")
                    .setAutoCancel(false)
                    .setLargeIcon(icon)
                    .setSmallIcon(R.drawable.logo)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentIntent(resultPendingIntent);

            Notification notification = new NotificationCompat.Builder(mContext, "xplore")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setLargeIcon(icon)
                    .setContentIntent(resultPendingIntent)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(icon)
                            .bigLargeIcon(null))
                    .build();



            mNotificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(intent, intent, importance);
                notificationChannel.enableLights(true);
               // notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                mBuilder.setChannelId(intent);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(0 /* Request Code */, notification);


            //another noti
            PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), resultIntent, 0);







            NotificationManager notificationManager =
                    (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

         //   notificationManager.notify(0, n);

        }
    public void OpenPLayStore(String title, String body, String packageName, Bitmap image, String targetUserType) {
        /**Creates an explicit intent for an Activity in your app**/


        //userType.equals(SharedData.LEVEL_1) && userType.equals(SharedData.LEVEL_2) &&
        //!((userType.equals(SharedData.LEVEL_1) && intent.equals("job")) || (userType.equals(SharedData.LEVEL_2) && intent.equals("job")))
        Intent resultIntent = null;

        if (true) {

            String message = "";



            message = body;

            resultIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            //resultIntent.putExtra("link",intent);


            title = title;


            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.logo);
            Bitmap logoBitmap=icon;
            if (image != null) {
                icon = image;
            }

            // newMsgNotification(mContext,title,message,image);


            mBuilder = new NotificationCompat.Builder(mContext);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentTitle("this hard code is working")
                    .setContentText(message)
                    .setAutoCancel(false)
                    .setLargeIcon(icon)
                    .setSmallIcon(R.drawable.logo)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentIntent(resultPendingIntent);

            Notification notification = new NotificationCompat.Builder(mContext, packageName)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(icon)
                    .setContentIntent(resultPendingIntent)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(icon)
                            .bigLargeIcon(null))
                    .build();



            mNotificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(packageName, packageName, importance);
                notificationChannel.enableLights(true);
                // notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                mBuilder.setChannelId(packageName);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(0 /* Request Code */, notification);


            //another noti
            PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), resultIntent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
            Notification n = new Notification.Builder(mContext)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.logo)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();


            n = new NotificationCompat.Builder(mContext, "1")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(icon)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(icon)
                            .bigLargeIcon(null))
                    .build();


            NotificationManager notificationManager =
                    (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

            //   notificationManager.notify(0, n);

        } else {

// use System.currentTimeMillis() to have a unique ID for the pending intent

        }

    }
    public void LinkOpenarNotification2(String title, String body, String intent, Bitmap image, String targetUserType) {
        /**Creates an explicit intent for an Activity in your app**/


        //userType.equals(SharedData.LEVEL_1) && userType.equals(SharedData.LEVEL_2) &&
        //!((userType.equals(SharedData.LEVEL_1) && intent.equals("job")) || (userType.equals(SharedData.LEVEL_2) && intent.equals("job")))
        Intent resultIntent = null;
        if (true) {

            String message = "";



            message = body;

            resultIntent = new Intent(mContext, LinkOpenarActivity.class);
            resultIntent.putExtra("link",intent);


            title = title;


            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.logo);
            Bitmap logoBitmap=icon;
            if (image != null) {
                icon = image;
            }

            // newMsgNotification(mContext,title,message,image);


            mBuilder = new NotificationCompat.Builder(mContext);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentTitle("this hard code is working")
                    .setContentText(message)
                    .setAutoCancel(false)
                    .setLargeIcon(icon)
                    .setSmallIcon(R.drawable.logo)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentIntent(resultPendingIntent);

            Notification notification = new NotificationCompat.Builder(mContext, intent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(icon)
                    .setContentIntent(resultPendingIntent)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(icon)
                            .bigLargeIcon(null))
                    .build();



            mNotificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(intent, intent, importance);
                notificationChannel.enableLights(true);
                // notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                mBuilder.setChannelId(intent);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(0 /* Request Code */, notification);


            //another noti
            PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), resultIntent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
            Notification n = new Notification.Builder(mContext)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.logo)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();


            n = new NotificationCompat.Builder(mContext, "1")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(icon)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(icon)
                            .bigLargeIcon(null))
                    .build();


            NotificationManager notificationManager =
                    (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

            //   notificationManager.notify(0, n);

        } else {

// use System.currentTimeMillis() to have a unique ID for the pending intent

        }

        }



}