package com.mpwd2.momomotus.ui.pages.home

import com.mpwd2.momomotus.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.home, "Home");
    object Game : NavigationItem("game", R.drawable.gamepad, "Game");
    object Leaderboard : NavigationItem("leaderboard", R.drawable.filter_variant, "Leaderboard");
    object Profile : NavigationItem("profile", R.drawable.account_multiple_plus, "Profile");
}