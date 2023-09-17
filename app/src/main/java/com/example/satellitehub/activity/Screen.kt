package com.example.satellitehub.activity

sealed class Screen(val route: String, val name: String) {
    data object Home : Screen("home", "Home")
    data object Detail : Screen("detail", "Detail")
}

