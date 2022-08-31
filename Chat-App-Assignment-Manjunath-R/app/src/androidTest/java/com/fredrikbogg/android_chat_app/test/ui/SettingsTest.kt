package com.fredrikbogg.android_chat_app.test.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.fredrikbogg.android_chat_app.test.robots.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SettingsTest: BaseTest() {

    @Before
    fun beforeTest(){
        if(!isLoggedIn){
            withStartRobot {
                goToLogin()
            } verifyThat {
                loginScreenIsShown(resources)
            }
            registerIdling()
            withLoginRobot {
                login(testData!!.validLoginData.email, testData!!.validLoginData.password)
                isLoggedIn = true
            } verifyThat {
                chatsScreenIsShown(resources)
            }
            unregisterIdling()
        }
    }

    @After
    fun afterTest(){
        if(isLoggedIn){
            withSettingsRobot {
                doLogout(BaseTest.resources)
            } verifyThat {
                startScreenIsShown(resources)
            }
        }
    }

    @Test
    fun test1_settingsAllBtnEnabled(){
        withSettingsRobot {
            goToSettings()
            settingsScreenIsShown(resources)
            allButtonsAreEnabled()
        }
    }

    @Test
    fun test2_settingsChangeStatus(){
        withSettingsRobot {
            goToSettings()
            settingsScreenIsShown(resources)
        } and {
            goToChangeStatus()
            allElementsArePresentInChangeStatusDialog()
            changeStatusAndSave(testData!!.changeStatus.newStatus)
        } andThen {
            verifyNewStatus(testData!!.changeStatus.newStatus)
        }
    }
}