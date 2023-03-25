package com.example.infomedicalstaff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.infomedicalstaff.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginFragment = LoginFragment()
        val fm : FragmentManager = supportFragmentManager
        fm.beginTransaction().add(R.id.main_layout, loginFragment).commit()
    }
}