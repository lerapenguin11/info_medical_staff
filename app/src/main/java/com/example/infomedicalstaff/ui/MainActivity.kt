package com.example.infomedicalstaff.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.UserModel
import com.example.infomedicalstaff.databinding.ActivityMainBinding
import com.example.infomedicalstaff.ui.fragments.LoginFragment
import com.example.infomedicalstaff.utilits.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        APP_ACTIVITY = this
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initFunc()
        hideKeyboard()
        //обновление статуса -> в сети
        AppStates.updateState(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()

        //обновление статуса -> не в сети
        AppStates.updateState(AppStates.OFFLINE)
    }

    private fun initFunc() {
        createLoginFragment()
        initFirebase()
        initUser()
    }

    //обновление данных
    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
            .addListenerForSingleValueEvent(AppValueEventListener{
                USER = it.getValue(UserModel :: class.java)?:UserModel()
            })
    }

    private fun createLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_layout, LoginFragment()).commit()
    }
}