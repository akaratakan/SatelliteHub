package com.example.satellitehub.util

fun String.getFirstSegment(): String {
    val delimiterIndex = indexOf("/")
    return if (delimiterIndex != -1) {
        substring(0, delimiterIndex)
    } else {
        this
    }
}