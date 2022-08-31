package com.fredrikbogg.android_chat_app.test.robots

import android.util.Log
import androidx.test.espresso.Espresso
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.utils.AutomationVariables

fun withUsersRobot(function: UserRobot.() -> Unit):UserRobot =
    UserRobot().apply(function)

infix fun UserRobot.verifyThat(function: UserRobotResult.() -> Unit):UserRobotResult
        = UserRobotResult().apply(function)

class UserRobot: BaseRobot() {

    /**
     * goes to users tab
     */
    fun goToUsers(){
        clickButton(R.id.navigation_users)
        Log.d(AutomationVariables.LOG_TAG,"Clicked on bottom navigation users tab")
    }

    /**
     * asserts number items present in recycler view is matching the given count or not
     * @param - pass the items to be checked
     */
    fun assertItemsInUsersRecyclerView(items:Int){
        assertRecyclerViewItems(R.id.usersRecyclerView,items)
        Log.d(AutomationVariables.LOG_TAG,"Verified users recycler view items")
    }

    /**
     * clicks on given user in the users recycler view
     * @param - pass the user to be clicked
     */
    fun clickOnGivenUser(user:String){
        clickOnRecyclerViewItemWithText(user)
        Log.d(AutomationVariables.LOG_TAG,"clicked on given user($user) in recycler view")
    }
}

class UserRobotResult:BaseRobot(){

    /**
     * verifies request sent button is disabled in the user profile screen
     */
    fun isRequestSentButtonDisabled(){
        if(!isButtonDisabled(R.id.requestSentButton)){
            Log.d(AutomationVariables.LOG_TAG,"Request sent button is NOT disabled")
        }
        Espresso.pressBack()
        Log.d(AutomationVariables.LOG_TAG,"Request sent button is disabled")
    }
}