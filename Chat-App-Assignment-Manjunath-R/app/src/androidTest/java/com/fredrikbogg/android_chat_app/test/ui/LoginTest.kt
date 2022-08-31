package com.fredrikbogg.android_chat_app.test.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.fredrikbogg.android_chat_app.test.robots.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginTest: BaseTest() {

    @Before
    fun beforeTest(){
        if(!isLoggedIn){
            withStartRobot {
                goToLogin()
            } verifyThat {
                loginScreenIsShown(resources)
            }
        }
    }

    @After
    fun afterTest(){
        if(isLoggedIn){
            withSettingsRobot {
                doLogout(resources)
            } verifyThat {
                startScreenIsShown(resources)
            }
        }
    }

    @Test
    fun test1_invalidLogin_withoutEmail(){
        withLoginRobot {
            inputPassword(testData!!.inValidLoginData.password)
            clickLogin()
        } verifyThat {
            snackBarIsShown("Invalid email format")
        }
    }

    @Test
    fun test2_invalidLogin_withoutPassword(){
        withLoginRobot {
            inputEmail(testData!!.inValidLoginData.email)
            clickLogin()
        } verifyThat {
            snackBarIsShown("Password is too short")
        }
    }

    @Test
    fun test3_invalidLogin_wrongEmail(){
        withLoginRobot {
            inputEmail(testData!!.inValidLoginData.email)
            inputPassword(testData!!.validLoginData.password)
            clickLogin()
        } verifyThat {
            snackBarForWrongEmailOrPwdIsShown(testData!!.snackBarError.wrongEmail)
        }
    }

    @Test
    fun test4_invalidLogin_wrongPassword(){
        withLoginRobot {
            inputEmail(testData!!.validLoginData.email)
            inputPassword(testData!!.inValidLoginData.password)
            clickLogin()
        } verifyThat {
            snackBarForWrongEmailOrPwdIsShown(testData!!.snackBarError.wrongPassword)
        }
    }

    @Test
    fun test5_validLogin(){
        registerIdling()
        withLoginRobot {
            inputEmail(testData!!.validLoginData.email)
            inputPassword(testData!!.validLoginData.password)
            clickLogin()
            isLoggedIn = true
        } verifyThat  {
            chatsScreenIsShown(resources)
            chatTitleDisplayed(testData!!.chat.chatTitle)
            chatUserDisplayed(testData!!.chat.chatUser)
            chatTimeDisplayed(testData!!.chat.chatTime)
        }
        unregisterIdling()
    }

}