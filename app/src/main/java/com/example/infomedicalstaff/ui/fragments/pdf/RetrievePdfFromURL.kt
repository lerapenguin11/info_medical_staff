package com.example.infomedicalstaff.ui.fragments.pdf

import android.annotation.SuppressLint
import android.os.AsyncTask
import com.github.barteksc.pdfviewer.PDFView
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RetrievePdfFromURL(pdfView: PDFView) :
    AsyncTask<String, Void, InputStream>() {

    @SuppressLint("StaticFieldLeak")
    val mypdfView: PDFView = pdfView

    override fun doInBackground(vararg params: String?): InputStream? {
        // on below line we are creating a variable for our input stream.
        var inputStream: InputStream? = null
        try {
            val url = URL(params.get(0))

            val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection

            if (urlConnection.responseCode == 200) {
                inputStream = BufferedInputStream(urlConnection.inputStream)
            }
        }

        catch (e: Exception) {
            e.printStackTrace()
            return null;
        }

        return inputStream;
    }

    override fun onPostExecute(result: InputStream?) {

        mypdfView.fromStream(result)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            .enableAnnotationRendering(false)
            .password(null)
            .scrollHandle(null)
            .enableAntialiasing(true)
            .spacing(0)
            .load()

    }
}