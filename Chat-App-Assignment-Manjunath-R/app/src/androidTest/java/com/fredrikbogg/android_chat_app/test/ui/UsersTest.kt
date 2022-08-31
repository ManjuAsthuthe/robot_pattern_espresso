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
class UsersTest: BaseTest() {

    @Before
    fun beforeTest() {
        if (!isLoggedIn) {
            withStartRobot {
                goToLogin()
            } verifyThat {
                loginScreenIsShown(resources)
            }

            registerIdling()
            withLoginRobot {
                login(testData!!.validLoginData.email, BaseTest.testData!!.validLoginData.password)
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
                doLogout(resources)
            } verifyThat {
                startScreenIsShown(resources)
            }
        }
    }

    @Test
    fun test1_AllUsersPresent(){
        withUsersRobot {
            goToUsers()
            assertItemsInUsersRecyclerView(2)
        }
    }

    @Test
    fun test2_RequestSentButtonDisabled(){
        withUsersRobot {
            goToUsers()
            clickOnGivenUser(testData!!.user.name)
        }verifyThat {
            isRequestSentButtonDisabled()
        }
    }
}