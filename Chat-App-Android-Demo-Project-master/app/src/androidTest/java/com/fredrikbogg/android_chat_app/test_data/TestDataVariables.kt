package com.fredrikbogg.android_chat_app.test_data

import androidx.test.platform.app.InstrumentationRegistry
import com.fredrikbogg.android_chat_app.test_data.models.TestData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class TestDataVariables {

    private var jsonAdapter: JsonAdapter<TestData>? = null

     init {
         val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
         jsonAdapter = moshi.adapter(TestData::class.java)
     }

    /**
    * Returns the test data parsed from JSON. jsons are found in ./androidTest/assets/
    * Usage : Init TestDataVariables class and call the getJsonData method with the JSON filename as string.
    * @param filename[String] Name of the Json file which is located in ./androidTest/assets/
    */

    fun getJsonData(filename: String): TestData? {
         return jsonAdapter?.fromJson(
         InstrumentationRegistry.getInstrumentation().context.assets.open(filename).bufferedReader().use
         { it.readText() }
         )
    }

}