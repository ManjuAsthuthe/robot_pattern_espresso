package com.fredrikbogg.android_chat_app.test.robots

import android.content.res.Resources
import android.util.Log
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.utils.AutomationVariables

fun withLoginRobot(function: LoginRobot.() -> Unit) = LoginRobot().apply(function)

infix fun LoginRobot.verifyThat(function: LoginRobotResult.() -> Unit): LoginRobotResult
        = LoginRobotResult().apply(function)

class LoginRobot: BaseRobot() {

    /**
     * enters email into email edit text field
     * @param - pass the email to be entered
     */
    fun inputEmail(email: String){
        fillEditText(R.id.editTextEmail, email)
        Log.d(AutomationVariables.LOG_TAG,"Entered value \"$email\" in email field")
    }

    /**
     * enters password into password edit text field
     * @param - pass the password to be entered
     */
    fun inputPassword(password: String){
        fillEditText(R.id.editTextPassword, password)
        Log.d(AutomationVariables.LOG_TAG,"Entered value \"$password\" in password field")
    }

    /**
     * clicks on login button
     */
    fun clickLogin(){
        clickButton(R.id.loginButton)
        sleep(6000)
        Log.d(AutomationVariables.LOG_TAG,"Clicked on login button in the login screen")
    }

    /**
     * performs login with given email and password
     * @param - pass the email and password to be entered
     */
    fun login(email:String, password:String){
        inputEmail(email)
        inputPassword(password)
        clickLogin()
        Log.d(AutomationVariables.LOG_TAG,"Login successful")
    }
}

class LoginRobotResult: BaseRobot() {

    /**
     * Checks whether chats screen is shown or not
     * @param - pass the resources to get the value in string format of the given string(from strings.xml)
     */
    fun chatsScreenIsShown(resources: Resources){
        verifyBottomNavigationScreenTitle(resources.getString(R.string.title_chats))
        Log.d(AutomationVariables.LOG_TAG,"Inside Chats Screen")
    }

    /**
     * Checks whether given chat title is displayed or not
     * @param - pass the title of the chat to be checked
     */
    fun chatTitleDisplayed(chatTitle: String){
        isTextDisplayed(R.id.messageText,chatTitle)
        Log.d(AutomationVariables.LOG_TAG,"Chat title displayed")
    }

    /**
     * Checks whether given chat user is displayed or not
     * @param - pass the user of the chat to be checked
     */
    fun chatUserDisplayed(chatUser: String){
        isTextDisplayed(R.id.displayNameText,chatUser)
        Log.d(AutomationVariables.LOG_TAG,"Chat user displayed")
    }

    /**
     * Checks whether given chat time is displayed or not
     * @param - pass the time of the chat to be checked
     */
    fun chatTimeDisplayed(chatTime: String){
        isTextDisplayed(R.id.timeText, chatTime)
        Log.d(AutomationVariables.LOG_TAG,"Chat time displayed")
    }
}
