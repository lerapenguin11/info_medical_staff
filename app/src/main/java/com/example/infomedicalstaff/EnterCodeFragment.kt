package com.example.infomedicalstaff

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.room.RoomDatabase
import com.example.infomedicalstaff.databinding.FragmentEnterCodeBinding
import com.example.infomedicalstaff.databinding.FragmentNewAuthenticationBinding
import com.example.infomedicalstaff.ui.fragments.HomeFragment
import com.example.infomedicalstaff.utilits.AUTH
import com.example.infomedicalstaff.utilits.AppTextWatcher
import com.example.infomedicalstaff.utilits.replaceFragment
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class EnterCodeFragment(val mPhoneNamber : String, val id : String) : Fragment() {
    private var _binding: FragmentEnterCodeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEnterCodeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.newEtRegCode.addTextChangedListener (AppTextWatcher{
            val string : String = binding.newEtRegCode.text.toString()
            if(string.length == 6){
                enterCode()
            }

        })
        }

    private fun enterCode() {
        val code = binding.newEtRegCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)

        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if(task.isSuccessful){
                verifiCode()
            } else Toast.makeText(activity, task.exception?.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun verifiCode() {
        Toast.makeText(activity, "Ok", Toast.LENGTH_LONG).show()
        replaceFragment(HomeFragment())
    }
}

