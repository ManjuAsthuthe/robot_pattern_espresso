package com.fredrikbogg.android_chat_app.test.ui

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.fredrikbogg.android_chat_app.test_data.TestDataVariables
import com.fredrikbogg.android_chat_app.test_data.models.TestData
import com.fredrikbogg.android_chat_app.ui.main.MainActivity
import com.fredrikbogg.android_chat_app.util.EspressoIdlingResource
import com.fredrikbogg.android_chat_app.utils.AutomationVariables
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.rules.TestName


open class BaseTest {

    @get:Rule
    var mActivityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(
        MainActivity::class.java)

    companion object{
        var testData:TestData? = null
        lateinit var resources: Resources
        private var testDataFileName = "TestData.json"
        var isLoggedIn:Boolean = false

        @get:Rule
        var name = TestName()

        @BeforeClass
        @JvmStatic
        fun setup(){
            resources = ApplicationProvider.getApplicationContext<Context?>().resources
            if(!setTestData(testDataFileName)){
                throw ExceptionInInitializerError("Unable to se the test data")
            }
        }

        @AfterClass
        @JvmStatic
        fun cleanUp(){
            //yet to be added
        }

        /**
         * Setting the test data
         * @param pass the json file name
         * @return boolean value
         **/
        private fun setTestData(json: String): Boolean {
            testData = TestDataVariables().getJsonData(json)
            Log.d(AutomationVariables.LOG_TAG, "Test Data is set")
            return true
        }

        fun registerIdling(){
            IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        }

        fun unregisterIdling(){
            IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        }
    }
}