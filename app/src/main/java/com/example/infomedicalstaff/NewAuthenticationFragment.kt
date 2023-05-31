package com.example.infomedicalstaff

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.infomedicalstaff.databinding.FragmentNewAuthenticationBinding
import com.example.infomedicalstaff.databinding.FragmentNewsFeedBinding
import com.example.infomedicalstaff.utilits.AUTH
import com.example.infomedicalstaff.utilits.replaceFragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class NewAuthenticationFragment : Fragment() {
    private var _binding: FragmentNewAuthenticationBinding? = null
    private val binding get() = _binding!!
    private lateinit var mPhoneNamber : String
    private lateinit var callback : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewAuthenticationBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                AUTH.signInWithCredential(p0).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity, "Добро пожаловать", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(activity, p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(mPhoneNamber, id))
            }
        }
        clickButton()
        authUser()
        sendCode()
    }

    private fun sendCode() {
        if(binding.newEtRegEmail.text.toString().isEmpty()){
            Toast.makeText(activity, "Введите номер", Toast.LENGTH_LONG).show()
        } else {
            authUser()
        }
    }

    private fun authUser() {
        mPhoneNamber = binding.newEtRegEmail.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhoneNamber,
            60,
            TimeUnit.SECONDS,
            activity as RegisterActivity,
            callback
        )
    }

    private fun clickButton() {
        binding.newBtSingnup.setOnClickListener { sendCode() }

    }
}