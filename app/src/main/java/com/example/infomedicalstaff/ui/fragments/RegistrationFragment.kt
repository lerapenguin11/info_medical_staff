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
import com.google.firebase.auth.FirebaseAuth

class RegistrationFragment : Fragment() {

    private var _binding : FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        binding.btnSingnup.setOnClickListener {
            val email = binding.etRegEmail.text.toString()
            val password = binding.etRegPassword.text.toString()
            val passwordTwo = binding.etSignupPasswordTwo.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && passwordTwo.isNotEmpty()){
                if(password == passwordTwo){
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if(it.isSuccessful){
                            val loginFragment = LoginFragment()
                            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
                            transaction.replace(R.id.main_layout, loginFragment)
                            transaction.commit()
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