package com.saba.gitrepositoriesdisplay.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.saba.gitrepositoriesdisplay.R
import com.saba.gitrepositoriesdisplay.utils.EspressoIdlingResource
import junit.framework.TestCase
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class GitRepoActivityTest{

    @get:Rule
    val activityRule = ActivityScenarioRule(GitRepoActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun isActivityInView() {

        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    // Visibility
    @Test
    fun isTitleTextVisible() {

        onView(withId(R.id.title_text))
            .check(matches(isDisplayed())) // method 1

        onView(withId(R.id.title_text))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE))) // method 2

    }

    @Test
    fun isTitleTextCorrect() {

        onView(withId(R.id.title_text))
            .check(matches(withText("Git repositories for user: ANDROID")))
    }

    @Test
    fun isRecyclerViewDisplayed(){
        onView(withId(R.id.git_recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.loading_spinner)).check(matches(not(isDisplayed())))
    }
}