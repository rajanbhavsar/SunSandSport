package com.sunsandsports

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sunsandsports.ui.ActivitySplash
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith


/**
 * This is the Application Unit Testing.
 * 1. We are running the App by below Unit Test.
 * 2. After that we are waiting for few moments until Api is called and response is available on Screen.
 * 3. After Response available we are verifying the Position and calling the detail screen.
 * 4. now we are waiting for few moments to verify the the Detail Screen is called for given Position.
 *
 * I developed this test case by learning from the Stackoverflow and some of the Medium links
 *
 */
@RunWith(AndroidJUnit4::class)
class FragmentHomeTest {

    @Test
    fun launchSplashScreen() {

        //Test Case to run the Main Screen/Launcher Screen
        ActivityScenario.launch(ActivitySplash::class.java)

        //Waiting for An Api is being called by Thread to sleep and wait
        Thread.sleep(15000)


        /*onView(withId(R.id.rvList))
            .check(matches(hasDescendant(withText("male"))))*/

        //Test case to perform click event on List Item with position as 5.
        onView(allOf(withId(R.id.mTextViewPostion), withText("5"))).perform(click());

        //Now The detail is Open and waiting for the Sometime to verify the above action are being performed.
        Thread.sleep(15000)

    }

}

