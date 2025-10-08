package com.example.thangnt.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thangnt.data.model.Item
import com.example.thangnt.data.repository.InMemoryRepository

class MainViewModel : ViewModel() {
    private val repository = InMemoryRepository()
    
    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items
    
    init {
        loadItems()
    }
    
    fun loadItems() {
        _items.value = repository.getAllItems()
    }
    
    fun addItem(item: Item) {
        repository.addItem(item)
        loadItems()
    }
    
    fun updateItem(position: Int, item: Item) {
        repository.updateItem(position, item)
        loadItems()
    }
    
    fun deleteItem(position: Int) {
        repository.deleteItem(position)
        loadItems()
    }
    
    fun getItem(position: Int): Item? {
        return repository.getItem(position)
    }
}
