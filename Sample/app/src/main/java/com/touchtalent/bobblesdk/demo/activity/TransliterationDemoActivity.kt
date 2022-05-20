package com.touchtalent.bobblesdk.demo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.touchtalent.bobble.transliteration.BobbleTransliterator
import com.touchtalent.bobblesdk.demo.R
import com.touchtalent.bobblesdk.demo.databinding.ActivityTransliterationDemoBinding

class TransliterationDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransliterationDemoBinding

    // Global instance of transliterator for the session
    private var bobbleTransliterator: BobbleTransliterator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransliterationDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Update transliterator instance when language changes
        binding.languageSelector.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.hindi -> createBobbleTransliterator(language = "hi")
                R.id.bangla -> createBobbleTransliterator(language = "bn")
            }
        }

        // Add text change listener on the edittext
        binding.editText.addTextChangedListener { editable ->
            val input = editable.toString()
            updateTransliteration(input)
        }

        binding.languageSelector.check(R.id.hindi)
    }

    private fun updateTransliteration(input: String) {
        // Evaluate list of transliterations for given input
        val transliterations: List<String> =
            bobbleTransliterator?.transliterate(input) ?: emptyList()
        // Update UI from the output
        binding.transliteratedText.text = transliterations.joinToString(",")
    }

    private fun createBobbleTransliterator(language: String) {
        // Close any existing bobbleTransliterator instance to cleanup memory
        bobbleTransliterator?.close()
        // Create new BobbleTransliterator instance for new language
        bobbleTransliterator = BobbleTransliterator(language)
        // Update transliterated text
        binding.editText.text?.toString()?.let { updateTransliteration(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        bobbleTransliterator?.close()
    }
}