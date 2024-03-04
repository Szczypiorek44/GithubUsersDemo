package com.example.githubusers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.features.details.detailsScreen
import com.example.features.details.navigateToDetails
import com.example.features.list.USER_LIST_ROUTE
import com.example.features.list.listScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = USER_LIST_ROUTE,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        listScreen(
            onUserClick = { navController.navigateToDetails(it) }
        )
        detailsScreen()
    }
}