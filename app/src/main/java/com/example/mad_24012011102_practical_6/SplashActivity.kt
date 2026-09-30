package com.example.mad_24012011102_practical_6

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mad_24012011102_practical_6.R

class SplashActivity : AppCompatActivity() , Animation.AnimationListener{
    lateinit var guniframeanimation : AnimationDrawable
    lateinit var imglogo : ImageView
    lateinit var guniAnimation: Animation
    private var hasStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imglogo = findViewById(R.id.imglogo)
        imglogo.setBackgroundResource(R.drawable.uvpce_animation_list)
        guniframeanimation = imglogo.background as AnimationDrawable
        guniAnimation = AnimationUtils.loadAnimation(this, R.anim.twin_animation)
        guniAnimation.setAnimationListener(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && !hasStarted){
            hasStarted = true
            // 1. Start frame-by-frame animation so logo parts appear one after another
            guniframeanimation.start()

            // 2. Calculate total duration for all frames to appear
            var frameDuration = 0
            for (i in 0 until guniframeanimation.numberOfFrames) {
                frameDuration += guniframeanimation.getDuration(i)
            }

            // 3. When all logo parts have appeared, trigger the rotation animation
            Handler(Looper.getMainLooper()).postDelayed({
                imglogo.startAnimation(guniAnimation)
            }, frameDuration.toLong())
        }
    }

    override fun onAnimationEnd(animation: Animation?) {
        // 4. When rotation ends, transition to MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    override fun onAnimationRepeat(animation: Animation?) {}

    override fun onAnimationStart(animation: Animation?) {}
}
