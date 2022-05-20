package com.touchtalent.bobblesdk.demo.activity

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.touchtalent.bobble.content.BobbleContentView
import com.touchtalent.bobblesdk.demo.databinding.ActivityContentDemoBinding
import com.touchtalent.bobblesdk.demo.databinding.DialogContentShareBinding

/**
 * Demo activity to demonstrate usage of Bobble Content
 */
class ContentDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentDemoBinding

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // BobbleContentView used in the layout activity_content_demo.xml
        val bobbleContentView: BobbleContentView = binding.contentView
        // Set listener on BobbleContentView to listen for clicks on content share
        bobbleContentView.setShareListener { uri ->
            // Uri pointing towards final PNG/WebP image
            // Demo dialog to show shared content
            dialog = ContentShareDialog(this, uri)
                .apply { show() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
    }

    inner class ContentShareDialog(context: Context, uri: Uri) : Dialog(context) {
        init {
            val binding = DialogContentShareBinding.inflate(LayoutInflater.from(context))
            setContentView(binding.root)
            Glide.with(binding.image)
                .load(uri)
                .into(binding.image)
            binding.title.text = "Shared content uri: $uri"
        }
    }
}

