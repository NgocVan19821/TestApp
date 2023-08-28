package com.lutech.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.btnStart
import kotlinx.android.synthetic.main.activity_main2.icon

class MainActivity2 : AppCompatActivity(), Animation.AnimationListener  {
    private lateinit var animMoveToTop: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        animMoveToTop = AnimationUtils.loadAnimation(applicationContext, R.anim.move)
        animMoveToTop.setAnimationListener(this)
        btnStart.setOnClickListener {
            icon.visibility = View.VISIBLE
            icon.startAnimation(animMoveToTop)
        }
    }

    override fun onAnimationStart(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
        if (animation === animMoveToTop) {
            Toast.makeText(applicationContext, "Animation Stopped", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAnimationRepeat(animation: Animation?) {
    }
}