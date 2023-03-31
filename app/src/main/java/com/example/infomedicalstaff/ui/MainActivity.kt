package com.example.infomedicalstaff.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.User
import com.example.infomedicalstaff.databinding.ActivityMainBinding
import com.example.infomedicalstaff.ui.fragments.LoginFragment
import com.example.infomedicalstaff.utilits.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.net.URI

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initFunc()
    }

    private fun initFunc() {
        createLoginFragment()
        initFirebase()
        initUser()
    }

    //обновление данных
    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(AppValueEventListener{
                USER = it.getValue(User :: class.java)?:User()
            })
    }

    private fun createLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_layout, LoginFragment()).commit()
    }
}