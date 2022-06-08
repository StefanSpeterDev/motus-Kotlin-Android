package com.mpwd2.momomotus

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mpwd2.momomotus.ui.pages.game.GameScreen
import com.mpwd2.momomotus.ui.pages.home.*
import com.mpwd2.momomotus.ui.pages.profile.ProfileScreen
import com.mpwd2.momomotus.ui.theme.MomomotusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Game,
        NavigationItem.Leaderboard,
        NavigationItem.Profile,
    );

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MomomotusTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { (Text(text = "La meilleure app du monde!!!!")) }
                        )
                    },
                    bottomBar = {
                        BottomNavigation(backgroundColor = Color.LightGray) {
                            val navBackStackEntry = navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry.value?.destination?.route
                            items.forEach { item ->
                                BottomNavigationItem(
                                    selected = currentRoute == item.route,
                                    unselectedContentColor = Color.Gray,
                                    selectedContentColor = Color.Blue,
                                    icon = {
                                        Icon(
                                            painterResource(id = item.icon),
                                            contentDescription = item.title
                                        )
                                    },
                                    label = { Text(text = item.title) },
                                    onClick = {
                                        navController.navigate(item.route) {

                                            // s'occupe de revenir en arrière
                                            navController.graph.startDestinationRoute?.let { route ->
                                                popUpTo(route) {
                                                    saveState = true
                                                }
                                            }
                                            // empêche de stacker les éclans à chaque clique
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                )
                            }
                        }
                    }
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.Game.route) {
            GameScreen()
        }
        composable(NavigationItem.Leaderboard.route) {
            LeaderboardScreen()
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen()
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