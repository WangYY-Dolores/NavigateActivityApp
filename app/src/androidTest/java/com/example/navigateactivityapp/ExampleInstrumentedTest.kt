package com.example.navigateactivityapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.Swipe
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.action.ViewActions


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.navigateactivityapp", appContext.packageName)
    }
    @Test
    fun testSwipeEast() {
        Espresso.onView(ViewMatchers.withId(R.id.home)).perform(ViewActions.swipeRight())

        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.east)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun testSwipeWest() {
        Espresso.onView(ViewMatchers.withId(R.id.home)).perform(ViewActions.swipeLeft())

        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.west)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}