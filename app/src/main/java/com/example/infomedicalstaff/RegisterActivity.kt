package com.example.infomedicalstaff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.infomedicalstaff.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    override fun onStart() {
        super.onStart()
        replaceFragment(NewAuthenticationFragment())
    }
}