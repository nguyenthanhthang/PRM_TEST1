package com.example.thangnt.data.repository

import com.example.thangnt.data.model.Item

class InMemoryRepository {
    private val items = mutableListOf<Item>()
    
    init {
        // Add some sample data
        items.addAll(listOf(
            Item(title = "Pizza Margherita", subtitle = "Pizza với cà chua, mozzarella và húng quế", price = 250000),
            Item(title = "Cà phê đen", subtitle = "Cà phê đen truyền thống", price = 25000),
            Item(title = "Bánh mì thịt nướng", subtitle = "Bánh mì với thịt nướng và rau tươi", price = 35000),
            Item(title = "Trà sữa trân châu", subtitle = "Trà sữa với trân châu đen", price = 45000),
            Item(title = "Phở bò", subtitle = "Phở bò truyền thống", price = 80000),
            Item(title = "Bún chả", subtitle = "Bún chả Hà Nội với thịt nướng", price = 70000),
            Item(title = "Gỏi cuốn", subtitle = "Gỏi cuốn tôm thịt tươi", price = 60000),
            Item(title = "Chè đậu đỏ", subtitle = "Chè đậu đỏ ngọt mát", price = 20000)
        ))
    }
    
    fun getAllItems(): List<Item> = items.toList()
    
    fun addItem(item: Item) {
        items.add(item)
    }
    
    fun updateItem(position: Int, item: Item) {
        if (position in 0 until items.size) {
            items[position] = item.copy(id = items[position].id)
        }
    }
    
    fun deleteItem(position: Int) {
        if (position in 0 until items.size) {
            items.removeAt(position)
        }
    }
    
    fun getItem(position: Int): Item? {
        return if (position in 0 until items.size) items[position] else null
    }
}
