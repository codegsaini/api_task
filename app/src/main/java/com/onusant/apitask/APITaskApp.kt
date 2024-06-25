package com.onusant.apitask

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.onusant.apitask.presentation.HomeScreen
import com.onusant.apitask.presentation.login.LoginScreen
import com.onusant.apitask.presentation.property.PropertyScreen

@Composable
fun APITaskApp() {
    val navController = rememberNavController()
    NavigationHost(navHostController = navController)
}

@Composable
fun NavigationHost(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = "home") {
        composable("login") {
            LoginScreen(onBackClick = { navHostController.popBackStack() })
        }

        composable("home") {
            HomeScreen(
                onTaskOneClick = { navHostController.navigate("login") },
                onTaskTwoClick = { navHostController.navigate("property") }
            )
        }

        composable("property") {
            PropertyScreen(onBackClick = { navHostController.popBackStack() })
        }
    }

}