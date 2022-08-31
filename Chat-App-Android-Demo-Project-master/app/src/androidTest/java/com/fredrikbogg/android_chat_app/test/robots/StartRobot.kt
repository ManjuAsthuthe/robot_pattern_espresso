package com.fredrikbogg.android_chat_app.test.robots

import android.content.res.Resources
import android.util.Log
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.utils.AutomationVariables


fun withStartRobot(function: StartRobot.() -> Unit) = StartRobot().apply(function)

infix fun StartRobot.verifyThat(function: StartRobotResult.() -> Unit):StartRobotResult
    = StartRobotResult().apply(function)

class StartRobot: BaseRobot() {

    /**
     * goes to login screen
     */
    fun goToLogin(){
        clickButton(R.id.loginButton)
        Log.d(AutomationVariables.LOG_TAG,"Clicked on login button in the start screen")
    }
}

class StartRobotResult: BaseRobot(){

    /**
     * Checks whether login screen is shown or not
     * @param - pass the resources to get the value in string format of the given string(from strings.xml)
     */
    fun loginScreenIsShown(resources: Resources){
        isTextDisplayed(resources.getString(R.string.title_login))
        Log.d(AutomationVariables.LOG_TAG,"Inside login Screen")
    }
}