@file:Suppress("INFERRED_TYPE_VARIABLE_INTO_POSSIBLE_EMPTY_INTERSECTION")

package ky.apelaralash.fontines.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ky.apelaralash.fontines.ui.screens.FontDetailScreen
import ky.apelaralash.fontines.ui.screens.HomeScreen
import ky.apelaralash.fontines.ui.screens.RecognitionScreen
import ky.apelaralash.fontines.ui.screens.ResultsScreen
import ky.apelaralash.fontines.ui.viewmodels.FontDetailViewModel
import ky.apelaralash.fontines.ui.viewmodels.HomeViewModel
import ky.apelaralash.fontines.ui.viewmodels.RecognitionViewModel
import ky.apelaralash.fontines.ui.viewmodels.ResultsViewModel

@Serializable
sealed class Screen {

    @Serializable
    data object Home : Screen()

    @Serializable
    data class Recognition(val imageUri: String) : Screen()

    @Serializable
    data class Results(val fontsJson: String) : Screen()

    @Serializable
    data class FontDetail(val fontJson: String) : Screen()
}

fun NavGraphBuilder.fontinesGraph(
    navController: NavController
) {
    composable<Screen.Home> {
        val viewModel: HomeViewModel = hiltViewModel()
        HomeScreen(
            viewModel = viewModel,
            onNavigateToRecognition = { imageUri ->
                navController.navigate(Screen.Recognition(imageUri.toString()))
            },
        )
    }

    composable<Screen.Recognition> {
        val viewModel: RecognitionViewModel = hiltViewModel()
        RecognitionScreen(
            viewModel = viewModel,
            onRecognitionComplete = { fontsJson ->
                navController.navigate(Screen.Results(fontsJson)) {
                    popUpTo(Screen.Home) { inclusive = false }
                }
            },
            onBack = {
                navController.popBackStack()
            }
        )
    }

    composable<Screen.Results> {
        val viewModel: ResultsViewModel = hiltViewModel()
        ResultsScreen(
            viewModel = viewModel,
            onFontClick = { fontJson ->
                navController.navigate(Screen.FontDetail(fontJson))
            },
            onBack = {
                navController.popBackStack(Screen.Home, inclusive = false)
            }
        )
    }

    composable<Screen.FontDetail> {
        val viewModel: FontDetailViewModel = hiltViewModel()
        FontDetailScreen(
            viewModel = viewModel,
            onBack = {
                navController.popBackStack()
            }
        )
    }
}
