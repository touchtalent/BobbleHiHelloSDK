package com.touchtalent.bobblesdk.demo.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.touchtalent.bobblesdk.demo.databinding.ActivityMainBinding

/**
 * Demo app to show-case usage of all BobbleSdk modules
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.head.setOnClickListener {
            // Launch demo activity to show-case use of Head APIs
            launch(HeadDemoActivity::class.java)
        }
        binding.content.setOnClickListener {
            // Launch demo activity to show-case use of Content APIs
            launch(ContentDemoActivity::class.java)
        }
        binding.transliteration.setOnClickListener {
            // Launch demo activity to show-case use of Transliteration APIs
            launch(TransliterationDemoActivity::class.java)
        }
    }

    private fun launch(activityClass: Class<*>) {
        startActivity(Intent(this, activityClass))
    }
}