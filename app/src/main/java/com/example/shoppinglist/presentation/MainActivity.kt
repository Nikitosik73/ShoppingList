package com.example.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.presentation.adapter.ShopListAdapter
import com.example.shoppinglist.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var shopAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this) {
            Log.d("test_of_load_data", it.toString())
            shopAdapter.shopList = it
        }
    }

    private fun setupRecyclerView() {
        shopAdapter = ShopListAdapter()
        with (binding) {
            with(recyclerViewShopItem) {
                adapter = shopAdapter
                recycledViewPool.setMaxRecycledViews(
                    ShopListAdapter.VIEW_TYPE_ENABLED, ShopListAdapter.MAX_POOL_SIZE
                )
                recycledViewPool.setMaxRecycledViews(
                    ShopListAdapter.VIEW_TYPE_DISABLED, ShopListAdapter.MAX_POOL_SIZE
                )
            }
        }
        setupLongClick()
        setupClick()
        removeToSwipe()
    }

    private fun removeToSwipe() {
        val itemTouchCallBack = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val shopItem = shopAdapter.shopList[position]
                viewModel.removeShopItem(shopItem)
            }
        }
        ItemTouchHelper(itemTouchCallBack).apply {
            attachToRecyclerView(binding.recyclerViewShopItem)
        }
    }

    private fun setupClick() {
        shopAdapter.onShopItemClickListener = {
            Log.d("ShowInfo on shopItem", it.toString())
        }
    }

    private fun setupLongClick() {
        shopAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}