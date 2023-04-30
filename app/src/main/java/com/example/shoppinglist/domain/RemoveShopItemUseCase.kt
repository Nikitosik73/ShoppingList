package com.example.shoppinglist.domain

class RemoveShopItemUseCase(private val shopItemRepository: ShopItemRepository) {

    fun removeShopItem(shopItem: ShopItem) {
        shopItemRepository.removeShopItem(shopItem)
    }
}