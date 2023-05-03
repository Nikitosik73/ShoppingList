package com.example.shoppinglist.presentation.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ShopListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.findViewById<TextView>(R.id.textViewShopItemName)
    val tvCount = itemView.findViewById<TextView>(R.id.textViewShopItemCount)
}