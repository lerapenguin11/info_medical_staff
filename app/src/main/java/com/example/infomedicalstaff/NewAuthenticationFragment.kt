package com.example.infomedicalstaff

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.infomedicalstaff.databinding.FragmentNewAuthenticationBinding
import com.example.infomedicalstaff.ui.MainActivity
import com.example.infomedicalstaff.utilits.AUTH
import com.example.infomedicalstaff.utilits.replaceFragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class NewAuthenticationFragment : Fragment() {
    private var _binding: FragmentNewAuthenticationBinding? = null
    private val binding get() = _binding!!
    private lateinit var mPhoneNumber : String
    private lateinit var resendingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callback : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewAuthenticationBinding.inflate(inflater, container, false)



        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        Toast.makeText(activity, "Добро пожаловать", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(activity, task.exception?.message.toString(), Toast.LENGTH_LONG).show()
                        Log.d("TAG", task.exception?.message.toString())
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(activity, p0.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(mPhoneNumber, id))
            }
        }
        binding.newBtSingnup.setOnClickListener { sendCose() }
    }

    private fun sendCose() {
        if(binding.phoneNumber.text.toString().isEmpty()){
            Toast.makeText(activity, "Введите номер", Toast.LENGTH_LONG).show()
        } else{
            authUser()
        }
    }

    private fun authUser() {
        mPhoneNumber = binding.phoneNumber.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhoneNumber,
            60,
            TimeUnit.SECONDS,
            activity as MainActivity,
            callback
        )
    }


}