package com.example.features.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val USER_DETAILS_ROUTE = "user_details_route"

internal const val USER_ID_ARG = "userId"


fun NavController.navigateToDetails(userId: Int) =
    navigate("$USER_DETAILS_ROUTE/$userId")

fun NavGraphBuilder.detailsScreen() {
    composable(
        route = "$USER_DETAILS_ROUTE/{$USER_ID_ARG}",
        arguments = listOf(
            navArgument(USER_ID_ARG) { type = NavType.IntType },
        )
    ) {
        val arguments = requireNotNull(it.arguments)
        val userId = arguments.getInt(USER_ID_ARG)
        DetailsRoute(userId)
    }
}
