package com.example.shoppinglist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : Adapter<ShopListAdapter.ShopListViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.shop_item_disabled,
            parent,
            false
        )
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = shopList[position]
        val status = if (shopItem.enabled) {
            "Active"
        } else {
            "Not active"
        }
        with(holder) {

            itemView.setOnLongClickListener {
                true
            }
        }
        if (shopItem.enabled) {
            holder.tvName.text = "${shopItem.name}: $status"
            holder.tvCount.text = shopItem.count.toString()
            holder.tvName.setTextColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_light)
            )
        }
    }

    override fun getItemCount() = shopList.size

    override fun onViewRecycled(holder: ShopListViewHolder) {
        super.onViewRecycled(holder)
        holder.tvName.text = ""
        holder.tvCount.text = ""
        holder.tvName.setTextColor(
            ContextCompat.getColor(holder.itemView.context, android.R.color.white)
        )
    }

    inner class ShopListViewHolder(itemView: View) : ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.textViewShopItemName)
        val tvCount = itemView.findViewById<TextView>(R.id.textViewShopItemCount)
    }
}