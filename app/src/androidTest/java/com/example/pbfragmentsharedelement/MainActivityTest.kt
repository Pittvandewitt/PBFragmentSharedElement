package com.example.pbfragmentsharedelement


import android.view.View
import android.view.ViewGroup
import android.window.BackEvent
import androidx.activity.BackEventCompat
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest2() {
        val view = onView(
            allOf(
                withId(R.id.box),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        view.perform(click())

        mActivityScenarioRule.scenario.onActivity {
            it.onBackPressedDispatcher.dispatchOnBackStarted(BackEventCompat(0.1F, 0.1F, 0.1F, BackEvent.EDGE_LEFT))
//            it.onBackPressedDispatcher.dispatchOnBackProgressed(BackEventCompat(0.2F, 0.1F, 0.5F, BackEvent.EDGE_LEFT))
            it.onBackPressedDispatcher.dispatchOnBackCancelled()
        }

        val appCompatImageButton = onView(
            allOf(
                withContentDescription(androidx.appcompat.R.string.abc_action_bar_up_description),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        synchronized(this) {
            (this as Object).wait(2_000)
        }
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
