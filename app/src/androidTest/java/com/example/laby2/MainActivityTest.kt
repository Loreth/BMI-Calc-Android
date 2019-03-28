package com.example.laby2


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityCountTest() {
        val massEditText = onView(withId(R.id.mass_input))
        massEditText.perform(replaceText("65"), closeSoftKeyboard())

        val heightEditText = onView(withId(R.id.height_input))
        heightEditText.perform(replaceText("170"), closeSoftKeyboard())

        val appCompatButton = onView(withId(R.id.count_button))
        appCompatButton.perform(click())

        val textView = onView(withId(R.id.bmi_value_text))
        textView.check(matches(withText("22.49")))
    }

    @Test
    fun mainActivityVisibilityTest() {
        onView(withId(R.id.mass_input)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.height_input)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.count_button)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.bmi_value_text)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.height_text_view)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.mass_text_view)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.bmi_main_image)).check(matches(ViewMatchers.isDisplayed()))

        //button invisible until bmi is calculated
        onView(withId(R.id.bmi_description_button)).check(matches(not(ViewMatchers.isDisplayed())))
    }
}
