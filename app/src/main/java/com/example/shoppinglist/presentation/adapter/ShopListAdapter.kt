package com.example.shoppinglist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : Adapter<ShopListAdapter.ShopListViewHolder>() {

    val list = listOf<ShopItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.shop_item_enabled, parent, false
        )
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = list[position]
        with(holder) {
            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()
            itemView.setOnLongClickListener {
                true
            }
        }
    }

    override fun getItemCount() = list.size

    inner class ShopListViewHolder(itemView: View) : ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.textViewShopItemName)
        val tvCount = itemView.findViewById<TextView>(R.id.textViewShopItemCount)
    }
}