package com.example.infomedicalstaff.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.databinding.ActivityMainBinding
import com.example.infomedicalstaff.ui.fragments.LoginFragment

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
    }

    private fun createLoginFragment() {
        val loginFragment = LoginFragment()
        val fm : FragmentManager = supportFragmentManager
        fm.beginTransaction().add(R.id.main_layout, loginFragment).commit()
    }
}