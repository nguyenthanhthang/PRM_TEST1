package com.example.thangnt

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thangnt.data.model.Item
import com.example.thangnt.databinding.ActivityMainBinding
import com.example.thangnt.ui.edit.EditActivity
import com.example.thangnt.ui.main.ItemAdapter
import com.example.thangnt.ui.main.MainViewModel
import com.example.thangnt.util.IntentKeys

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemAdapter
    private val viewModel: MainViewModel by viewModels()
    
    private val editActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            IntentKeys.RESULT_OK_ADD -> {
                val item = result.data?.getParcelableExtra<Item>(IntentKeys.EXTRA_ITEM)
                item?.let { viewModel.addItem(it) }
            }
            IntentKeys.RESULT_OK_EDIT -> {
                val item = result.data?.getParcelableExtra<Item>(IntentKeys.EXTRA_ITEM_UPDATED)
                val position = result.data?.getIntExtra(IntentKeys.EXTRA_POSITION, -1) ?: -1
                if (item != null && position != -1) {
                    viewModel.updateItem(position, item)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupRecyclerView()
        setupFab()
        observeViewModel()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }
    
    private fun setupRecyclerView() {
        adapter = ItemAdapter(
            onItemClick = { item, position ->
                openEditActivity(item, position)
            },
            onItemLongClick = { position ->
                showDeleteDialog(position)
            }
        )
        
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }
    
    private fun setupFab() {
        binding.fab.setOnClickListener {
            openEditActivity(null, -1)
        }
    }
    
    private fun observeViewModel() {
        viewModel.items.observe(this, Observer { items ->
            adapter.submitList(items)
            updateEmptyState(items.isEmpty())
        })
    }
    
    private fun updateEmptyState(isEmpty: Boolean) {
        binding.emptyState.visibility = if (isEmpty) android.view.View.VISIBLE else android.view.View.GONE
        binding.recyclerView.visibility = if (isEmpty) android.view.View.GONE else android.view.View.VISIBLE
    }
    
    private fun openEditActivity(item: Item?, position: Int) {
        val intent = Intent(this, EditActivity::class.java).apply {
            if (item != null) {
                putExtra(IntentKeys.EXTRA_MODE, IntentKeys.MODE_EDIT)
                putExtra(IntentKeys.EXTRA_ITEM, item)
                putExtra(IntentKeys.EXTRA_POSITION, position)
            } else {
                putExtra(IntentKeys.EXTRA_MODE, IntentKeys.MODE_ADD)
            }
        }
        editActivityLauncher.launch(intent)
    }
    
    private fun showDeleteDialog(position: Int) {
        val item = viewModel.getItem(position)
        if (item != null) {
            AlertDialog.Builder(this)
                .setTitle("Xóa món")
                .setMessage("Bạn có chắc chắn muốn xóa \"${item.title}\"?")
                .setPositiveButton("Xóa") { _, _ ->
                    viewModel.deleteItem(position)
                }
                .setNegativeButton("Hủy", null)
                .show()
        }
    }
}
