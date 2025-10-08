package com.example.thangnt.ui.edit

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thangnt.data.model.Item
import com.example.thangnt.databinding.ActivityEditBinding
import com.example.thangnt.util.IntentKeys

class EditActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityEditBinding
    private var isEditMode = false
    private var currentItem: Item? = null
    private var position = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupInputValidation()
        handleIntent()
        setupButtons()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupInputValidation() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }
        }
        
        binding.titleEditText.addTextChangedListener(textWatcher)
    }
    
    private fun validateInput() {
        val title = binding.titleEditText.text.toString().trim()
        val isValid = title.isNotEmpty()
        binding.saveButton.isEnabled = isValid
    }
    
    private fun handleIntent() {
        val mode = intent.getStringExtra(IntentKeys.EXTRA_MODE)
        isEditMode = mode == IntentKeys.MODE_EDIT
        
        if (isEditMode) {
            currentItem = intent.getParcelableExtra(IntentKeys.EXTRA_ITEM)
            position = intent.getIntExtra(IntentKeys.EXTRA_POSITION, -1)
            currentItem?.let { populateFields(it) }
        }
    }
    
    private fun populateFields(item: Item) {
        binding.titleEditText.setText(item.title)
        binding.subtitleEditText.setText(item.subtitle ?: "")
        binding.priceEditText.setText(item.price?.toString() ?: "")
    }
    
    private fun setupButtons() {
        binding.cancelButton.setOnClickListener {
            setResult(IntentKeys.RESULT_CANCELED)
            finish()
        }
        
        binding.saveButton.setOnClickListener {
            if (validateAndSave()) {
                finish()
            }
        }
    }
    
    private fun validateAndSave(): Boolean {
        val title = binding.titleEditText.text.toString().trim()
        val subtitle = binding.subtitleEditText.text.toString().trim()
        val priceText = binding.priceEditText.text.toString().trim()
        
        if (title.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show()
            binding.titleEditText.requestFocus()
            return false
        }
        
        if (title.length < 2) {
            Toast.makeText(this, "Tên món ăn phải có ít nhất 2 ký tự", Toast.LENGTH_SHORT).show()
            binding.titleEditText.requestFocus()
            return false
        }
        
        val price = if (priceText.isNotEmpty()) {
            try {
                val priceValue = priceText.toInt()
                if (priceValue < 0) {
                    Toast.makeText(this, "Giá không được âm", Toast.LENGTH_SHORT).show()
                    binding.priceEditText.requestFocus()
                    return false
                }
                priceValue
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show()
                binding.priceEditText.requestFocus()
                return false
            }
        } else null
        
        val item = if (isEditMode && currentItem != null) {
            currentItem!!.copy(
                title = title,
                subtitle = if (subtitle.isNotEmpty()) subtitle else null,
                price = price
            )
        } else {
            Item(
                title = title,
                subtitle = if (subtitle.isNotEmpty()) subtitle else null,
                price = price
            )
        }
        
        val resultIntent = Intent().apply {
            if (isEditMode) {
                putExtra(IntentKeys.EXTRA_ITEM_UPDATED, item)
                putExtra(IntentKeys.EXTRA_POSITION, position)
                setResult(IntentKeys.RESULT_OK_EDIT, this)
            } else {
                putExtra(IntentKeys.EXTRA_ITEM, item)
                setResult(IntentKeys.RESULT_OK_ADD, this)
            }
        }
        
        return true
    }
}
