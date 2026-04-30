package ky.apelaralash.fontines.ui.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ky.apelaralash.fontines.ui.screens.FontDetailScreen
import ky.apelaralash.fontines.ui.screens.HomeScreen
import ky.apelaralash.fontines.ui.screens.RecognitionScreen
import ky.apelaralash.fontines.ui.screens.ResultsScreen
import androidx.navigation.NavHostController

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Recognition : Screen("recognition")
    object Results : Screen("results")
    object FontDetail : Screen("font_detail/{fontId}") {
        fun createRoute(fontId: String) = "font_detail/$fontId"
    }
}

fun NavGraphBuilder.fontinesGraph(
    navController: NavController
) {
    composable(route = Screen.Home.route) {
        HomeScreen(
            onNavigateToRecognition = { imageUri ->
                navController.navigate(Screen.Recognition.route)
            },
            onImageSelected = { imageUri ->
                navController.navigate(Screen.Recognition.route)
            }
        )
    }
    
    composable(route = Screen.Recognition.route) {
        RecognitionScreen(
            onRecognitionComplete = {
                navController.navigate(Screen.Results.route) {
                    popUpTo(Screen.Home.route) { inclusive = false }
                }
            },
            onBack = {
                navController.popBackStack()
            }
        )
    }
    
    composable(route = Screen.Results.route) {
        ResultsScreen(
            onFontClick = { fontId ->
                navController.navigate(Screen.FontDetail.createRoute(fontId))
            },
            onBack = {
                navController.popBackStack(Screen.Home.route, inclusive = false)
            }
        )
    }
    
    composable(
        route = Screen.FontDetail.route,
        arguments = listOf(
            navArgument("fontId") { type = NavType.StringType }
        )
    ) {
        FontDetailScreen(
            onBack = {
                navController.popBackStack()
            }
        )
    }
}
