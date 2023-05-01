package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopItemRepository {

    fun getShopList(): LiveData<List<ShopItem>>

    fun addShopItem(shopItem: ShopItem)

    fun removeShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId: Int): ShopItem
}