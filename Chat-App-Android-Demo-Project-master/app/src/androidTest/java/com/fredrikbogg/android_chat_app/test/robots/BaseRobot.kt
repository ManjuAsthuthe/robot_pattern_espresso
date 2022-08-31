package com.fredrikbogg.android_chat_app.test.robots

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.utils.AutomationVariables
import org.hamcrest.CoreMatchers.not


open class BaseRobot {

    /**
     * fills the edit text with given value
     * @param - pass the resource id of the edit text to be filled and
     *          value to be entered
    */
    fun fillEditText(resId: Int, text: String): ViewInteraction =
        onView(withId(resId)).perform(replaceText(text), closeSoftKeyboard())

    /**
     * clicks on the button
     * @param - pass the resource id of the button to be clicked
     */
    fun clickButton(resId: Int): ViewInteraction =
        onView(withId(resId)).perform(click())

    /**
     * verifies for the given text presence
     * @param - pass the text of the view to be checked
     */
    fun isTextDisplayed(text: String){
        onView(withText(text)).check(matches(isDisplayed()))
    }

    /**
     * verifies for the given text presence
     * @param - pass the text of the view to be checked
     */
    fun isTextDisplayed(resId: Int, text: String) {
        onView(withId(resId)).check(matches(withText(text)))
    }

    /**
     * verifies the screen title of the bottom navigation tabs
     * (chats,users,notification,settings tab)
     * @param - pass the title of the screen to be verified
     */
    fun verifyBottomNavigationScreenTitle(title: String){
        onView(withContentDescription(title)).check(matches(isDisplayed()))
    }

    /**
     * Checks whether button is enabled or not
     * @param - pass the resource id of the button to be checked
     */
    fun isButtonEnabled(resId: Int){
        onView(withId(resId)).check(matches(isEnabled()))
    }

    /**
     * verifies whether button is disabled or not
     * @param - pass the resource id of the button to be checked
     * @return - true if disabled or else false
     */
    fun isButtonDisabled(resId: Int):Boolean{
        return try{
            onView(withId(resId)).check(matches(not(isEnabled())))
            true
        }catch (e: Exception){
            false
        }
    }

    /**
     * sleeps the thread for given time
     * @param - sleep time
     */
    fun sleep(sleepTime : Long) = apply{
        Thread.sleep(sleepTime)
        Log.d(AutomationVariables.LOG_TAG,"Sleeping for $sleepTime ms")
    }

    /**
     * Checks whether snack bar is shown with given text
     * @param - snack bar text to be verified
     */
    fun snackBarIsShown(text: String){
        try{
            onView(withId(R.id.snackbar_text))
                .check(matches(withText(text)))
            Log.d(AutomationVariables.LOG_TAG,"Snack bar text($text) validated")
        }catch (e: Exception){
            Log.d(AutomationVariables.LOG_TAG, "Exception when checking snack bar text($text) -> ${e.printStackTrace()}")
        }
    }

    /**
     * Verifies title on alert dialog
     * @param - pass the title of the alert dialog to be verified
     */
    fun verifyTitleOnAlertDialog(title: String){
        onView(withText(title)).check(matches(isDisplayed()))
    }

    /**
     * Verifies title on alert dialog
     * @param - pass the title of the alert dialog to be verified
     */
    fun enterValueInAlertDialog(value: String){
        onView(withHint(" new status")).perform(typeText(value))
    }

    /**
     * Verifies button on alert dialog
     * @param - pass the text of the button to be verified
     */
    fun verifyButtonsOnAlertDialog(text: String){
        onView(withText(text)).check(matches(isDisplayed()))
    }

    /**
     * Clicks on alert dialog button
     * @param - pass the text of the button to be clicked
     */
    fun clickOnButtonsInAlertDialog(text: String){
        onView(withText(text)).perform(click())
    }

    /**
     * Assert number of items present in recycler view
     * @param - pass the resource id of the recycler view
     *          and number of items to be checked
     */
    fun assertRecyclerViewItems(resId: Int, count:Int){
        onView(withId(resId)).check(matches(hasChildCount(count)))
    }

    /**
     * Clicks on recycler item with given text
     * @param - pass the text of the item to be clicked
     */
    fun clickOnRecyclerViewItemWithText(text:String){
        onView(withText(text)).perform(click())
    }

}