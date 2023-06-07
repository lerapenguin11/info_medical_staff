package com.example.infomedicalstaff.ui.fragments.pdf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.infomedicalstaff.databinding.FragmentPdfBinding
import com.example.infomedicalstaff.ui.fragments.SanitaryRulesFragment
import com.example.infomedicalstaff.utilits.replaceFragment

class PDFFragment : Fragment() {
    private var _binding : FragmentPdfBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container : ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPdfBinding.inflate(inflater, container, false)

        val displayMessage = arguments?.getString("title")
        binding.tvToolbarNameFile.text = displayMessage

        val displayFile = arguments?.getString("file")
        RetrievePdfFromURL(binding.pdfView).execute(displayFile)


        binding.btArrowChatList.setOnClickListener { replaceFragment(SanitaryRulesFragment()) }
        return binding.root
    }
}