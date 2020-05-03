package com.rafiq.newxplore.Data;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rafiq.newxplore.utlis.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class myFunctions {
    public interface FileDownlaodListener {
        void onDownloaded(String uri);
    }

    FileDownlaodListener fileDownlaodListener;

    public void setFileDownlaodListener(FileDownlaodListener listener) {
        this.fileDownlaodListener = listener;
    }

    public String downloadLink;
    static InputStream input;
    public static FileDownlaodListener OwnFileDownloadListener;

    public void askPermission(String link, Activity activity, FileDownlaodListener listener) {
        OwnFileDownloadListener = listener;
        Dexter.withActivity((Activity) activity)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now

                            // final DownloadTask downloadTask = new DownloadTask(activity);
                            //  downloadTask.execute(link);
                            String fileName="FILE_"+System.currentTimeMillis()+".pdf";


                            PRDownloader.download(link, Utils.getRootDirPath(activity), fileName)
                                    .build()
                                    .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                        @Override
                                        public void onStartOrResume() {

                                        }
                                    })
                                    .setOnPauseListener(new OnPauseListener() {
                                        @Override
                                        public void onPause() {

                                        }
                                    })
                                    .setOnCancelListener(new OnCancelListener() {
                                        @Override
                                        public void onCancel() {

                                        }
                                    })
                                    .setOnProgressListener(new OnProgressListener() {
                                        @Override
                                        public void onProgress(Progress progress) {
                                            Log.i("apr", "prog "+progress.totalBytes);

                                        }
                                    }).start(new OnDownloadListener() {
                                                 @Override
                                                 public void onDownloadComplete() {
                                                     Log.i("apr", "downloaded");
                                                     OwnFileDownloadListener.onDownloaded(Utils.getRootDirPath(activity)+"/"+fileName);
                                                     Log.i("apr", Utils.getRootDirPath(activity)+"/"+fileName);
                                                 }

                                                 @Override
                                                 public void onError(Error error) {
                                                     Log.i("apr", "error \n"+error.getServerErrorMessage());

                                                 }
                                             }
                            );


                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    public static class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                Log.i("apr", "do in backgournd downaoaing mkl");
                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream("/sdcard/xploreFile.pdf");

                Log.i("apr", "file name dicided ");


                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                    Log.i("apr", "file written in ");
                }
            } catch (Exception e) {
                Log.i("apr", "my marked error " + "\n" + e.getLocalizedMessage());
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false

        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                //   Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                OwnFileDownloadListener.onDownloaded("/sdcard/xploreFile.pdf");
            // OwnFileDownloadListener.onDownloaded(result);

            //pdfView.fromUri(Uri.parse("/sdcard/pricelist.pdf"));
            //pdfView.fromStream(input).load();
            //pdfView.fromFile( new File( "/sdcard/pricelist.pdf" )).load();
            // pdfView.fromFile(new File("/sdcard/xploreFile.pdf")).load();
            // fileToShare = new File("/sdcard/xploreFile.pdf");


        }
    }
}
