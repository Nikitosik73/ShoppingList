package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopItemBinding
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.presentation.viewmodel.ShopItemViewModel

class ShopItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopItemBinding
    private lateinit var viewModel: ShopItemViewModel

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parseIntent()

        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        addTextChangeListener()
        launchRightMode()
        observeViewModel()
    }

    private fun launchRightMode() {
        when (screenMode) {
            EDIT_MODE -> launchEditMode()
            ADD_MODE -> launchAddMode()
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.error_edit_name)
            } else {
                null
            }
            binding.tilName.error = message
        }
        viewModel.errorInputCount.observe(this) {
            val message = if (it) {
                getString(R.string.error_edit_count)
            } else {
                null
            }
            binding.tilCount.error = message
        }
        viewModel.closeScreen.observe(this) {
            finish()
        }
    }

    private fun addTextChangeListener() {
        binding.editName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.editCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun launchAddMode() {
        binding.buttonSave.setOnClickListener {
            viewModel.addShopItem(
                binding.editName.text?.toString(),
                binding.editCount.text?.toString()
            )
        }
    }

    private fun launchEditMode() {

        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this) {
            binding.editName.setText(it.name)
            binding.editCount.setText(it.count.toString())
        }
        binding.buttonSave.setOnClickListener {
            viewModel.editShopItem(
                binding.editName.text?.toString(),
                binding.editCount.text?.toString()
            )
        }
    }

    private fun parseIntent() {
        // проверка содержит ли intent пареметр EXTRA_SCREEN_MODE
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        // проверка на правильно переданный mode
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != ADD_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        // проверка на переданный параметр id элемента, если мы находимся в режиме редактирования
        if (screenMode == EDIT_MODE) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val EDIT_MODE = "edit_mode"
        private const val ADD_MODE = "add_mode"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {

            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, ADD_MODE)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {

            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            intent.putExtra(EXTRA_SCREEN_MODE, EDIT_MODE)
            return intent
        }
    }
}