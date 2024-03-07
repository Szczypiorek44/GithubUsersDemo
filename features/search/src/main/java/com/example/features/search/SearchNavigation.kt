package com.example.features.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val SEARCH_USER_ROUTE = "search_user_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) =
    navigate(SEARCH_USER_ROUTE, navOptions)

fun NavGraphBuilder.searchScreen() {
    composable(route = SEARCH_USER_ROUTE) {
        SearchRoute()
    }
}
