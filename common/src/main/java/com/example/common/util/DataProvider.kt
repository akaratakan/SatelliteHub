package com.example.common.util

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataProvider @Inject constructor(
    private val moshi: Moshi,
    @ApplicationContext private val context: Context
) {

    fun <T>readJSONFromAssets(dataClass: Class<T>, fileName: String): T? {
        val jsonAdapter: JsonAdapter<T> = moshi.adapter(dataClass)

        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            jsonAdapter.fromJson(jsonString)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

}