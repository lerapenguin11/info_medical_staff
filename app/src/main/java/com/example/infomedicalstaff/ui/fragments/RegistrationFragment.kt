package com.example.infomedicalstaff.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.databinding.FragmentRegistrationBinding
import com.example.infomedicalstaff.utilits.*
import com.google.firebase.auth.FirebaseAuth

class RegistrationFragment : Fragment() {

    private var _binding : FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        binding.btnSingnup.setOnClickListener {
            val email = binding.etRegEmail.text.toString()
            val password = binding.etRegPassword.text.toString()
            val passwordTwo = binding.etSignupPasswordTwo.text.toString()
            initFirebase()

            if(email.isNotEmpty() && password.isNotEmpty() && passwordTwo.isNotEmpty()){
                if(password == passwordTwo){
                    AUTH.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if(it.isSuccessful){

                            val uid = AUTH.currentUser?.uid.toString()
                            val dateMap = mutableMapOf<String, Any>()
                            dateMap[CHILD_ID] = uid
                            dateMap[CHILD_EMAIL] = email
                            dateMap[CHILD_USER_NAME] = uid

                            REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                                .addOnCompleteListener {it2 ->
                                    if(it2.isSuccessful){
                                        val loginFragment = LoginFragment()
                                        val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
                                        transaction.replace(R.id.main_layout, loginFragment)
                                        transaction.commit()
                                    } else Toast.makeText(context, "Произошла проблема", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                } else{
                    Toast.makeText(context, "Пароль не совпадает", Toast.LENGTH_LONG).show()
                }
            }
        }

        return binding.root
    }
}