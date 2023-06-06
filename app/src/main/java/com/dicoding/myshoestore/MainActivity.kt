package com.dicoding.myshoestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dicoding.myshoestore.ui.about.ShoeAboutScreen
import com.dicoding.myshoestore.ui.detail.ShoeDetailScreen
import com.dicoding.myshoestore.ui.home.MyShoeStoreApp
import com.dicoding.myshoestore.ui.navigation.Screen
import com.dicoding.myshoestore.ui.theme.MyShoesStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyShoesStoreTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.Home.route) {
                        composable(Screen.Home.route) {
                            MyShoeStoreApp(navController)
                        }
                        composable(Screen.About.route) {
                            ShoeAboutScreen(navController, onBackClick = {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Home.route) {
                                        inclusive = true
                                    }
                                }
                            })
                        }
                        composable(Screen.DetailShoe.route) { backStackEntry ->
                            val shoeId = backStackEntry.arguments?.getString("shoeId")?.toInt()
                            if (shoeId != null) {
                                ShoeDetailScreen(navController, shoeId,
                                    onBackClick = {
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(Screen.Home.route) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
