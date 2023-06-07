package com.example.infomedicalstaff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.infomedicalstaff.databinding.FragmentEnterCodeBinding
import com.example.infomedicalstaff.ui.fragments.HomeFragment
import com.example.infomedicalstaff.utilits.*
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class EnterCodeFragment(val phoneNumber: String, val id: String) : Fragment() {
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
        binding.idOtp.addTextChangedListener (AppTextWatcher{
            val string = binding.idOtp.text.toString()
            if(string.length==6){
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = binding.idOtp.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = phoneNumber
                dateMap[CHILD_USER_NAME] = phoneNumber

                REF_DATABASE_ROOT.child(NODE_PHONES).child(phoneNumber).setValue(uid)
                    .addOnSuccessListener {
                        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                            .addOnSuccessListener {
                                Toast.makeText(activity, "Добро пожаловать", Toast.LENGTH_LONG)
                                    .show()
                                replaceFragment(HomeFragment())
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    activity,
                                    task.exception?.message.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }

            } else Toast.makeText(activity, task.exception?.message.toString(), Toast.LENGTH_LONG).show()
        }


    }
}

