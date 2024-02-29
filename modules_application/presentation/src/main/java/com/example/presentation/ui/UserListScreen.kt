package com.example.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.UserListState
import com.example.presentation.UserListViewModel
import com.example.presentation.theme.GithubUsersTheme

@Composable
fun UserListRoute(viewModel: UserListViewModel = UserListViewModel()) {
    val userListState by viewModel.userListState.collectAsStateWithLifecycle()

    UserListScreen(
        userListState = userListState
    )
}

@Composable
fun UserListScreen(userListState: UserListState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (userListState) {
            is UserListState.Loading -> {
                Text(text = "Loading...")
            }

            is UserListState.Success -> {
                LazyColumn {
                    items(userListState.userList) {
                        Text(text = it)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GithubUsersTheme {
        UserListScreen(userListState = UserListState.Loading)
    }
}