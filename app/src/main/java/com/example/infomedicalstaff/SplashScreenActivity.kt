package com.example.infomedicalstaff

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.airbnb.lottie.LottieAnimationView
import com.example.infomedicalstaff.ui.MainActivity

class SplashScreenActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var lottieAnimationView: LottieAnimationView =  findViewById(R.id.anim)

        lottieAnimationView.animate().translationY(3500f).setDuration(1000).setStartDelay(5000)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
    }
}