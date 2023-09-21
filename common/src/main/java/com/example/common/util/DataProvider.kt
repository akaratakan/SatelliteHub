package com.example.common.util

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataProvider @Inject constructor(
    val moshi: Moshi,
    @ApplicationContext val context: Context
) {

    inline fun <reified T> readJSONFromAssets(fileName: String): T? {
        val jsonAdapter: JsonAdapter<T> = this.moshi.adapter(T::class.java)
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val jsonString = String(buffer, Charsets.UTF_8)
        return jsonAdapter.fromJson(jsonString)
    }

}