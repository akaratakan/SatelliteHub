package com.example.satellitehub.activity

import com.example.satellitehub.R

sealed class Screen(val route: String, val name: String,val resId:Int) {
    data object Home : Screen("home", "Home", R.string.toolbar_title_home)
    data object Detail : Screen("detail", "Detail", R.string.toolbar_title_detail)
}

