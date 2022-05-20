package com.touchtalent.bobblesdk.demo.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.touchtalent.bobble.head.BobbleHead
import com.touchtalent.bobble.head.BobbleHeadSdk
import com.touchtalent.bobblesdk.demo.databinding.ActivityHeadDemoBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * Demo activity to demonstrate usage of Head creation activity
 */
class HeadDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeadDemoBinding
    private lateinit var activityLauncher: ActivityResultLauncher<Unit>

    private val coroutineScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeadDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create activity contract to launch head creation activity and listen for result
        val activityContract = BobbleHeadSdk.getHeadCreator()
            .newBuilder(this)
            .getActivityContract()

        // Register the contract on activity and set listener.
        // See - https://developer.android.com/training/basics/intents/result
        activityLauncher = registerForActivityResult(activityContract) { createdBobbleHeadId ->
            processHead(createdBobbleHeadId)
        }

        binding.createHead.setOnClickListener {
            // Launch head creation activity
            activityLauncher.launch(Unit)
        }
        binding.checkHeads.setOnClickListener {
            // Launch demo activity to show-case use of Head Manager APIs
            startActivity(Intent(this, HeadDetailsActivity::class.java))
        }
    }

    private fun processHead(createdBobbleHeadId: Long?) = coroutineScope.launch {
        // Get head object from createdHeadId, failure if no head was created
        val headResult: Result<BobbleHead> = createdBobbleHeadId?.let {
            BobbleHeadSdk.getHeadManager().getHeadById(createdBobbleHeadId)
        } ?: Result.failure(Exception("Null head id received, no head created"))

        headResult.onSuccess { bobbleHead ->
            binding.headImage.visibility = View.VISIBLE
            binding.headDetails.visibility = View.VISIBLE
            Glide.with(this@HeadDemoActivity)
                .load(bobbleHead.headPath)
                .into(binding.headImage)
            binding.headDetails.text = buildString {
                append("headId: ${bobbleHead.headId}\n")
                append("width: ${bobbleHead.width}\n")
                append("height: ${bobbleHead.height}\n")
                append("ageGroup: ${bobbleHead.ageGroup}\n")
                append("gender: ${bobbleHead.gender}\n")
            }
        }.onFailure {
            binding.headImage.visibility = View.GONE
            binding.headDetails.visibility = View.VISIBLE
            binding.headDetails.text = "Failed to create head"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}