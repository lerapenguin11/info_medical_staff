package com.example.infomedicalstaff

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentTransaction
import com.example.infomedicalstaff.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var auth : FirebaseAuth

    @SuppressLint("CommitTransaction", "UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException :: class.java)

                if(account != null){
                    firebaseAuthWithGoogle(account.idToken!!) //регистрация
                }
            } catch (e: ApiException){
                Log.d("AuthTag", "Google signIn error")
            }
        }

        binding.tvRegistration.setOnClickListener {
            val registrationFragment = RegistrationFragment()
            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.main_layout, registrationFragment)
            transaction.commit()
        }

        //button google
        binding.btnGoogleLogin.setOnClickListener {
            signInWithGoogle()

            val homeFragment = HomeFragment()
            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.main_layout, homeFragment)
            transaction.commit()
        }

        binding.btnSingnup.setOnClickListener {
            val email = binding.etRegEmail.text.toString()
            val password = binding.etSignupPasswordTwo.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        val homeFragment = HomeFragment()
                        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
                        transaction.replace(R.id.main_layout, homeFragment)
                        transaction.commit()
                    }
                }
            }
        }

        return binding.root
    }

    private fun signInWithGoogle(){
        val singInClient = getClient()
        launcher.launch(singInClient.signInIntent)
    }

    //вынести в moxy
    private fun getClient() : GoogleSignInClient{
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun firebaseAuthWithGoogle(idToken : String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("AuthTag", "Google signIn done")
            } else Log.d("AuthTag", "Google signIn error")
        }
    }
}