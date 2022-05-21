package com.touchtalent.bobblesdk.demo.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.touchtalent.bobble.head.BobbleHead
import com.touchtalent.bobble.head.BobbleHeadSdk
import com.touchtalent.bobblesdk.demo.adapters.HeadsAdapter
import com.touchtalent.bobblesdk.demo.databinding.ActivityHeadDetailsBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Demo activity to demonstrate usage of head manager APIs
 */
class HeadDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeadDetailsBinding
    private val coroutineScope = MainScope()
    private val headAdapter = HeadsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeadDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HeadDetailsActivity)
            adapter = headAdapter
        }
        setupHeads()
    }

    private fun setupHeads() {
        coroutineScope.launch {
            // Listen for list of heads present in Head module, empty list is returned for no heads
            val getHeadsFlow: Flow<List<BobbleHead>> =
                BobbleHeadSdk.getHeadManager().getAllHeads()
            // Update UI with list of heads
            getHeadsFlow.collectLatest {
                headAdapter.data = it
                binding.noHeadsFound.visibility =
                    if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        }
        coroutineScope.launch {
            // Listen for change in primary head within the head module
            val getPrimaryHeadFlow: Flow<Long?> =
                BobbleHeadSdk.getHeadManager().getPrimaryHeadId()
            // Update UI for primary head
            getPrimaryHeadFlow.collectLatest {
                it?.let { headAdapter.primary = it }
            }
        }
        headAdapter.itemClickListener = {
            // Update primary head within the module. This head will be used to create content
            BobbleHeadSdk.getHeadManager().setPrimaryHead(it.headId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}