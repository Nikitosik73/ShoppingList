package com.example.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.presentation.adapter.ShopListAdapter
import com.example.shoppinglist.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this) {
            Log.d("test_of_load_data", it.toString())
            adapter.shopList = it
        }
    }

    private fun setupRecyclerView() {
        adapter = ShopListAdapter()
        binding.recyclerViewShopItem.adapter = adapter
    }
}