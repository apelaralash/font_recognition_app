@file:Suppress("INFERRED_TYPE_VARIABLE_INTO_POSSIBLE_EMPTY_INTERSECTION")

package ky.apelaralash.fontines.ui.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.Serializable
import ky.apelaralash.fontines.domain.model.FontMatch
import ky.apelaralash.fontines.ui.screens.FontDetailScreen
import ky.apelaralash.fontines.ui.screens.HomeScreen
import ky.apelaralash.fontines.ui.screens.RecognitionScreen
import ky.apelaralash.fontines.ui.screens.ResultsScreen
import ky.apelaralash.fontines.ui.viewmodels.FontDetailViewModel
import ky.apelaralash.fontines.ui.viewmodels.HomeViewModel
import ky.apelaralash.fontines.ui.viewmodels.RecognitionViewModel
import ky.apelaralash.fontines.ui.viewmodels.ResultsViewModel
import java.lang.reflect.Type
import kotlin.jvm.java
import kotlin.reflect.typeOf
import kotlin.to

sealed class Screen(val route: String) {

    @Serializable
    data object Home : Screen("home")

    @Serializable
    data class Recognition(val imageUri: String) : Screen("recognition/{$imageUri}")

    @Serializable
    data class Results(val fonts: FontMatchArguments) : Screen("results")

    @Serializable
    data object FontDetail : Screen("font_detail/{fontId}") {
        fun createRoute(fontId: String) = "font_detail/$fontId"
    }
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

    composable<Screen.Recognition>(
        typeMap = mapOf(
            typeOf<FontMatchArguments>() to navType<FontMatchArguments>()
        )
    ) {
        val viewModel: RecognitionViewModel = hiltViewModel()
        RecognitionScreen(
            viewModel = viewModel,
            onRecognitionComplete = { fonts ->
                navController.navigate(Screen.Results(FontMatchArguments(fonts))) {
                    popUpTo(Screen.Home.route) { inclusive = false }
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
            onFontClick = { fontId ->
                navController.navigate(Screen.FontDetail.createRoute(fontId))
            },
            onBack = {
                navController.popBackStack(Screen.Home.route, inclusive = false)
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

inline fun <reified T : java.io.Serializable?> navType(
    isNullableAllowed: Boolean = false,
    gson: Gson = Gson(),
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getSerializable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getSerializable(key) as? T
        }
    }

    override fun parseValue(value: String): T {
        val deserializedResult = gson.fromJson(value, T::class.java)
        return deserializedResult
    }

    override fun serializeAsValue(value: T): String {
        return if (value == null) {
            ""
        } else {
            gson.toJson(value)
        }
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        if (value == null) {
            bundle.putSerializable(key, null)
        } else {
            bundle.putSerializable(key, value)
        }
    }
}
