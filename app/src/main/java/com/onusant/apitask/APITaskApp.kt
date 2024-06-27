package com.onusant.apitask

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.onusant.apitask.data.repository.PreferenceRepository
import com.onusant.apitask.presentation.home.HomeScreen
import com.onusant.apitask.presentation.login.LoginScreen
import com.onusant.apitask.presentation.property.PropertyScreen
import com.onusant.apitask.presentation.property.view.ViewPropertyScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

val Context.datastore: DataStore<Preferences> by preferencesDataStore("local_datastore")

@Composable
fun APITaskApp() {
    val navController = rememberNavController()
    NavigationHost(navHostController = navController)
}

@Composable
fun NavigationHost(navHostController: NavHostController) {

    val context = LocalContext.current
    val preferenceRepository = PreferenceRepository(context.datastore)
    var startDestination by remember { mutableStateOf("auth") }

    // Synchronous user value for cold start
    val user = runBlocking {
        preferenceRepository.getPreference(stringPreferencesKey("user")).first()
    }
    startDestination = if (user.isNullOrEmpty()) "auth" else "main"

    // Observing user for rest of app life
    val userFlow = preferenceRepository.getPreference(stringPreferencesKey("user"))
        .collectAsState("").value

    LaunchedEffect(userFlow) {
        startDestination = if (userFlow.isNullOrEmpty()) "auth" else "main"
    }

    NavHost(navController = navHostController, startDestination = startDestination) {
        navigation(route = "auth", startDestination = "login") {
            composable("login") {
                LoginScreen()
            }
        }

        navigation(route = "main", startDestination = "home") {
            composable("home") {
                HomeScreen(
                    onRedirectToPropertiesScreen = { navHostController.navigate("property") },
                    onLogoutSuccess = {
                        navHostController.navigate("auth") {
                            popUpTo("auth") { inclusive = true }
                        }
                    },
                    onPropertyRedirect = { id ->
                        navHostController.navigate("property?id=$id")
                    }
                )
            }

            composable("property") {
                PropertyScreen(
                    onBackClick = { navHostController.popBackStack() },
                    onPropertyRedirect = { id -> navHostController.navigate("property?id=$id") }
                )
            }

            composable(
                route = "property?id={id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.StringType }
                )
            ) {
                ViewPropertyScreen(onBackClick = { navHostController.popBackStack() })
            }
        }
    }

}