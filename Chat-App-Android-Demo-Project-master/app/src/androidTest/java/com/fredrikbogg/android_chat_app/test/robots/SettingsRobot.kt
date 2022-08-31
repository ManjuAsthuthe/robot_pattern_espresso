package com.fredrikbogg.android_chat_app.test.robots

import android.content.res.Resources
import android.util.Log
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.utils.AutomationVariables

fun withSettingsRobot(function: SettingsRobot.() -> Unit):SettingsRobot =
    SettingsRobot().apply(function)

infix fun SettingsRobot.andThen(function: SettingsRobot.() -> Unit):SettingsRobot
        = SettingsRobot().apply(function)

infix fun SettingsRobot.and(function: SettingsRobot.() -> Unit):SettingsRobot
        = SettingsRobot().apply(function)

infix fun SettingsRobot.verifyThat(function: SettingsRobotResult.() -> Unit):SettingsRobotResult
    = SettingsRobotResult().apply(function)

class SettingsRobot: BaseRobot() {

    /**
     * navigates to settings tab
     */
    fun goToSettings(){
        clickButton(R.id.navigation_settings)
        Log.d(AutomationVariables.LOG_TAG,"Clicked on bottom navigation settings tab")
    }

    /**
     * Checks whether settings screen is shown or not
     * @param - pass the resources to get the value in string format of the given string(from strings.xml)
     */
    fun settingsScreenIsShown(resources: Resources){
        verifyBottomNavigationScreenTitle(resources.getString(R.string.title_settings))
        Log.d(AutomationVariables.LOG_TAG,"Inside settings Screen")
    }

    /**
     * goes to settings tab and clicks on logout
     * @param - pass the resources to get the value in string format of the given string(from strings.xml)
     */
    fun doLogout(resources: Resources){
        goToSettings()
        settingsScreenIsShown(resources)
        clickButton(R.id.logoutButton)
        sleep(5000)
        Log.d(AutomationVariables.LOG_TAG,"Clicked on logout button in the settings screen")
    }

    /**
    * clicks on change status button
    */
    fun goToChangeStatus(){
        clickButton(R.id.changeStatusButton)
        Log.d(AutomationVariables.LOG_TAG,"Clicked on change status button")
    }

    /**
     * verifies whether all buttons inside settings screen are enabled
     */
    fun allButtonsAreEnabled(){
        isButtonEnabled(R.id.changeImageButton)
        Log.d(AutomationVariables.LOG_TAG,"Change Image button is enabled")

        isButtonEnabled(R.id.changeStatusButton)
        Log.d(AutomationVariables.LOG_TAG,"Change Status button is enabled")

        isButtonEnabled(R.id.logoutButton)
        Log.d(AutomationVariables.LOG_TAG,"Logout button is enabled")
    }

    /**
     * verifies whether all elements inside change status alert dialog are enabled
     */
    fun allElementsArePresentInChangeStatusDialog(){
        verifyTitleOnAlertDialog("Status:")
        Log.d(AutomationVariables.LOG_TAG,"Status is present in change status alert dialog")

        verifyButtonsOnAlertDialog("OK")
        Log.d(AutomationVariables.LOG_TAG,"OK button is present in change status alert dialog")

        verifyButtonsOnAlertDialog("CANCEL")
        Log.d(AutomationVariables.LOG_TAG,"CANCEL button is present in change status alert dialog")
    }

    /**
     * changes the status by entering new value in the alert dialog and clicking on OK
     * @param - newStatus to be entered
     */
    fun changeStatusAndSave(newStatus: String){
        enterValueInAlertDialog(newStatus)
        clickOnButtonsInAlertDialog("OK")
        Log.d(AutomationVariables.LOG_TAG,"changed status and saved")
    }

    /**
     * verifies newly changed status in the settings screen
     * @param - pass the newly changes status to be verified
     */
    fun verifyNewStatus(newStatus: String){
        isTextDisplayed(R.id.statusText,newStatus)
        Log.d(AutomationVariables.LOG_TAG,"new status is verified")
    }
}

class SettingsRobotResult: BaseRobot(){

    /**
     * Checks whether start screen is shown or not
     * @param - pass the resources to get the value in string format of the given string(from strings.xml)
     */
    fun startScreenIsShown(resources: Resources){
        isTextDisplayed(resources.getString(R.string.title_start))
        Log.d(AutomationVariables.LOG_TAG,"Inside start Screen")
    }
}