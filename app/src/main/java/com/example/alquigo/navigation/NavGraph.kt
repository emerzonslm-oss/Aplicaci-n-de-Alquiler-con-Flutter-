package com.example.alquigo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.alquigo.ui.auth.AuthViewModel
import com.example.alquigo.ui.auth.LoginScreen
import com.example.alquigo.ui.auth.RegisterScreen
import com.example.alquigo.ui.auth.UiEvent
import com.example.alquigo.ui.home.HomeScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object AddProperty : Screen("add_property")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateToHome -> {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
                is UiEvent.NavigateToLogin -> {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            }
        }
    }

    val startDestination = if (viewModel.isUserLoggedIn()) Screen.Home.route else Screen.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = viewModel,
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = viewModel,
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToAddProperty = { navController.navigate(Screen.AddProperty.route) }
            )
        }
        composable(Screen.AddProperty.route) {
            com.example.alquigo.ui.property.AddPropertyScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
