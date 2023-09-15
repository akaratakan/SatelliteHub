package com.example.common

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException

class DataProvider {


    fun <T>readJSONFromAssets(dataClass: Class<T>,context: Context, fileName: String): T? {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

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