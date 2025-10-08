package com.example.thangnt.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thangnt.R
import com.example.thangnt.data.model.Item

class ItemAdapter(
    private val onItemClick: (Item, Int) -> Unit,
    private val onItemLongClick: (Int) -> Unit
) : ListAdapter<Item, ItemAdapter.ItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.titleText)
        private val subtitleText: TextView = itemView.findViewById(R.id.subtitleText)
        private val priceText: TextView = itemView.findViewById(R.id.priceText)
        private val itemImage: ImageView = itemView.findViewById(R.id.itemImage)

        fun bind(item: Item, position: Int) {
            titleText.text = item.title
            subtitleText.text = item.subtitle ?: ""
            priceText.text = if (item.price != null) "${item.price}đ" else ""
            
            // Set default image if no image resource provided
            itemImage.setImageResource(R.drawable.ic_launcher_foreground)

            itemView.setOnClickListener {
                onItemClick(item, position)
            }

            itemView.setOnLongClickListener {
                onItemLongClick(position)
                true
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}
