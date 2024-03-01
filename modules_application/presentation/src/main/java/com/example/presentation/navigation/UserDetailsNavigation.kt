package com.example.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.presentation.ui.UserDetailsRoute

const val USER_DETAILS_ROUTE = "user_details_route"

internal const val USER_ID_ARG = "userId"


fun NavController.navigateToUserDetails(userId: Int) =
    navigate("$USER_DETAILS_ROUTE/$userId")

fun NavGraphBuilder.userDetailsScreen() {
    composable(
        route = "$USER_DETAILS_ROUTE/{$USER_ID_ARG}",
        arguments = listOf(
            navArgument(USER_ID_ARG) { type = NavType.IntType },
        )
    ) {
        val userId = it.arguments?.getInt(USER_ID_ARG)
        UserDetailsRoute(userId)
    }
}
