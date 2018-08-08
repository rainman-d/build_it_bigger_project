package com.udacity.gradle.androidtesting;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.drainey.jokelib.JokeUtils;
import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.R;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by rainman-d on 8/6/18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        mIdlingResource = mActivityRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void testGetJokeAsync(){
        onView(withId(R.id.launch_joke_btn)).perform(click());

        String joke = mActivityRule.getActivity().getReturnedJoke();
        Assert.assertNotNull("Joke should not be null", joke);
        Assert.assertTrue("Joke should not be an empty string", !joke.isEmpty());

        // verify joke activity launched and the joke is displayed
        String jokeFromLibrary = JokeUtils.getJoke();
        Assert.assertNotNull(jokeFromLibrary);
        onView(withId(R.id.tv_joke_label)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_joke_text)).check(matches(withText(jokeFromLibrary)));
    }

    @After
    public void tearDown(){
        if(mIdlingResource != null){
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
