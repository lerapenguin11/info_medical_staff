package com.example.infomedicalstaff.ui.fragments.pdf

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.infomedicalstaff.databinding.FragmentPdfBinding
import com.github.barteksc.pdfviewer.PDFView
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class PDFFragment : Fragment() {
    private var _binding : FragmentPdfBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPdfBinding.inflate(inflater, container, false)

        val displayMessage = arguments?.getString("title")
        binding.tvToolbarNameFile.text = displayMessage

        val displayFile = arguments?.getString("file")
        RetrievePDFFromURL(binding.pdfView).execute(displayFile)


        return binding.root
    }

    class RetrievePDFFromURL(pdfView: PDFView) :
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
}