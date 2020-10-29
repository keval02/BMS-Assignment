package com.bmstest.app.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bmstest.app.R
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        titleTV.startAnimation(fadeInAnimation)

        val handler = Handler()
        handler.postDelayed(
            {
                intent = Intent(this, MovieListingActivity::class.java)
                startActivity(intent)
                finish()
            },
            3000
        )

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
}