package com.touchtalent.bobblesdk.demo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.touchtalent.bobblesdk.demo.databinding.ItemHeadBinding
import com.touchtalent.bobble.head.BobbleHead

@SuppressLint("NotifyDataSetChanged")
class HeadsAdapter() :
    RecyclerView.Adapter<HeadsViewHolder>() {

    var itemClickListener: ((item: BobbleHead) -> Unit)? = null

    var primary: Long = -1
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var data: List<BobbleHead> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadsViewHolder {
        return HeadsViewHolder(
            ItemHeadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HeadsViewHolder, position: Int) {
        val bobbleHead = data[position]
        holder.binding.primaryIndicator.visibility =
            if (primary == bobbleHead.headId) VISIBLE else GONE
        holder.binding.root.setOnClickListener { itemClickListener?.invoke(bobbleHead) }
        Glide.with(holder.binding.headImage)
            .load(bobbleHead.headPath)
            .into(holder.binding.headImage)
        holder.binding.headText.text = buildString {
            append("headId: ${bobbleHead.headId}\n")
            append("width: ${bobbleHead.width}\n")
            append("height: ${bobbleHead.height}\n")
            append("ageGroup: ${bobbleHead.ageGroup}\n")
            append("gender: ${bobbleHead.gender}\n")
        }
    }

    override fun getItemCount() = data.size

}

class HeadsViewHolder(val binding: ItemHeadBinding) : RecyclerView.ViewHolder(binding.root)