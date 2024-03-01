package com.example.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.User
import com.example.presentation.R
import com.example.presentation.UserListState
import com.example.presentation.UserListViewModel
import com.example.presentation.theme.GithubUsersTheme

@Composable
fun UserListRoute(
    onUserClick: (Int) -> Unit,
    viewModel: UserListViewModel = hiltViewModel()
) {
    val userListState by viewModel.userListState.collectAsStateWithLifecycle()

    UserListScreen(
        onUserClick = onUserClick,
        userListState = userListState
    )
}

@Composable
fun UserListScreen(
    onUserClick: (Int) -> Unit,
    userListState: UserListState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (userListState) {
            is UserListState.Loading -> {
                Text(text = stringResource(R.string.loading))
            }

            is UserListState.Success -> {
                UserListLazyColumn(userListState.userList, onUserClick)
            }
        }
    }
}

@Composable
fun UserListLazyColumn(
    userList: List<User>,
    onUserClick: (Int) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
    ) {
        items(
            items = userList,
            key = { it.id },
            itemContent = { UserRow(it, onUserClick) })
    }
}

@Composable
fun UserRow(
    user: User,
    onUserClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(vertical = 4.dp)
            .background(color = Color.LightGray)
            .border(1.dp, Black)
            .clickable(onClick = { onUserClick(user.id) })
    ) {
        Text(
            text = user.name,
            color = Black,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserListScreenPreview() {
    GithubUsersTheme {
        UserListScreen(
            userListState = UserListState.Success(fakeUserList),
            onUserClick = {}
        )
    }
}

private val fakeUserList = listOf(
    User(1, "John"),
    User(2, "Ann"),
    User(3, "Bob"),
    User(4, "Julia"),
    User(5, "Barbara"),
)