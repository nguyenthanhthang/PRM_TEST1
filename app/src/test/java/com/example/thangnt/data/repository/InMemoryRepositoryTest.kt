package com.example.thangnt.data.repository

import com.example.thangnt.data.model.Item
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class InMemoryRepositoryTest {
    
    private lateinit var repository: InMemoryRepository
    
    @Before
    fun setUp() {
        repository = InMemoryRepository()
    }
    
    @Test
    fun `test add item`() {
        val item = Item(title = "Test Item", subtitle = "Test Description", price = 10000)
        val initialSize = repository.getAllItems().size
        
        repository.addItem(item)
        
        val items = repository.getAllItems()
        assertEquals(initialSize + 1, items.size)
        assertTrue(items.contains(item))
    }
    
    @Test
    fun `test update item`() {
        val items = repository.getAllItems()
        assertTrue("Repository should have sample data", items.isNotEmpty())
        
        val originalItem = items[0]
        val updatedItem = originalItem.copy(title = "Updated Title", price = 50000)
        
        repository.updateItem(0, updatedItem)
        
        val updatedItems = repository.getAllItems()
        assertEquals("Updated Title", updatedItems[0].title)
        assertEquals(50000, updatedItems[0].price)
        assertEquals(originalItem.id, updatedItems[0].id) // ID should remain the same
    }
    
    @Test
    fun `test delete item`() {
        val initialSize = repository.getAllItems().size
        assertTrue("Repository should have sample data", initialSize > 0)
        
        repository.deleteItem(0)
        
        val items = repository.getAllItems()
        assertEquals(initialSize - 1, items.size)
    }
    
    @Test
    fun `test get item`() {
        val items = repository.getAllItems()
        assertTrue("Repository should have sample data", items.isNotEmpty())
        
        val item = repository.getItem(0)
        assertNotNull(item)
        assertEquals(items[0], item)
    }
    
    @Test
    fun `test get item with invalid position`() {
        val item = repository.getItem(-1)
        assertNull(item)
        
        val items = repository.getAllItems()
        val invalidItem = repository.getItem(items.size)
        assertNull(invalidItem)
    }
}
