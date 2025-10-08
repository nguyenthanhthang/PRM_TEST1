package com.example.thangnt

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.thangnt.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testMainActivityDisplaysCorrectly() {
        // Check if toolbar is displayed
        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))
        
        // Check if FAB is displayed
        onView(withId(R.id.fab))
            .check(matches(isDisplayed()))
        
        // Check if RecyclerView is displayed
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testFabOpensEditActivity() {
        // Click on FAB
        onView(withId(R.id.fab))
            .perform(click())
        
        // Check if EditActivity is opened by looking for its elements
        onView(withId(R.id.titleEditText))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testAddNewItem() {
        // Click on FAB to add new item
        onView(withId(R.id.fab))
            .perform(click())
        
        // Fill in the form
        onView(withId(R.id.titleEditText))
            .perform(typeText("Test Item"))
        
        onView(withId(R.id.subtitleEditText))
            .perform(typeText("Test Description"))
        
        onView(withId(R.id.priceEditText))
            .perform(typeText("10000"))
        
        // Save the item
        onView(withId(R.id.saveButton))
            .perform(click())
        
        // Verify we're back to main activity
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testCancelAddItem() {
        // Click on FAB to add new item
        onView(withId(R.id.fab))
            .perform(click())
        
        // Cancel the operation
        onView(withId(R.id.cancelButton))
            .perform(click())
        
        // Verify we're back to main activity
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }
}
