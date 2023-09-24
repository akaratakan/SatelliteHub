package com.example.common.util

import android.content.Context
import com.squareup.moshi.Moshi
import timber.log.Timber
import java.io.IOException
import java.io.InputStream

class ProviderUtil(private val context: Context, val moshi: Moshi) {
    fun readJSONFromAssets(fileName: String): String {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            Timber.e(e, "Read json file error")
            ""
        }
    }

    inline fun <reified T> parseJson(jsonData: String): T? {
        val kvAdapter = moshi.adapter(T::class.java).lenient()
        return kvAdapter.fromJson(jsonData)
    }

}