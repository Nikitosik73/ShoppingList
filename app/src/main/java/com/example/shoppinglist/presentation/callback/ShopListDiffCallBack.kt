package com.example.shoppinglist.presentation.callback

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglist.domain.ShopItem

class ShopListDiffCallBack(
    private val oldList: List<ShopItem>,
    private val newList: List<ShopItem>
): DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size
    // сравнивает сами объекты
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }
    // сранвивает поля объектов
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}