package com.example.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.presentation.ui.ListRoute

const val USER_LIST_ROUTE = "user_list_route"

fun NavController.navigateToList(navOptions: NavOptions? = null) =
    navigate(USER_LIST_ROUTE, navOptions)

fun NavGraphBuilder.listScreen(onUserClick: (Int) -> Unit) {
    composable(route = USER_LIST_ROUTE) {
        ListRoute(onUserClick)
    }
}
