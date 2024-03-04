package com.example.features.list

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val USER_LIST_ROUTE = "user_list_route"

fun NavController.navigateToList(navOptions: NavOptions? = null) =
    navigate(USER_LIST_ROUTE, navOptions)

fun NavGraphBuilder.listScreen(onUserClick: (Int) -> Unit) {
    composable(route = USER_LIST_ROUTE) {
        ListRoute(onUserClick)
    }
}
