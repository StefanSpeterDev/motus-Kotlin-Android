package com.mpwd2.momomotus

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mpwd2.momomotus.ui.navigation.NavigationKeys
import com.mpwd2.momomotus.ui.pages.home.HomePage
import com.mpwd2.momomotus.ui.pages.login.LoginScreen
import com.mpwd2.momomotus.ui.pages.signup.SignUpScreen
import com.mpwd2.momomotus.ui.theme.MomomotusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MomomotusTheme {
                NavigationApp()
            }
        }
    }
}

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationKeys.Route.SIGN_UP) {
        composable(NavigationKeys.Route.SIGN_UP) {
            SignUpScreen(navController= navController)
        }
        composable(NavigationKeys.Route.LOGIN) {
            LoginScreen(navController= navController)
        }
        composable(NavigationKeys.Route.HOME) {
            HomePage(navController= navController)
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MomomotusTheme {
        Greeting("Android")
    }
}