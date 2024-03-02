package com.example.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.User
import com.example.presentation.R
import com.example.presentation.theme.GithubUsersTheme
import com.example.presentation.viewmodels.ListViewModel

@Composable
fun ListRoute(
    onUserClick: (Int) -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {
    val listState by viewModel.userListState.collectAsStateWithLifecycle()
    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()

    ListScreen(
        userListState = listState,
        loadingState = loadingState,
        onUserClick = onUserClick,
        onScrolledToBottom = { viewModel.fetchNextUsers() }
    )
}

@Composable
fun ListScreen(
    userListState: List<User>,
    loadingState: Boolean,
    onUserClick: (Int) -> Unit,
    onScrolledToBottom: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EndlessLazyColumn(
            items = userListState,
            key = { it.id },
            isLoading = loadingState,
            onScrolledToBottom = onScrolledToBottom,
            itemContent = { UserItem(it, onUserClick) },
            loadingItemContent = { LoadingItem() })
    }
}

@Composable
fun UserItem(
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

@Composable
fun LoadingItem() {
    Text(
        text = stringResource(R.string.loading),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun <T> EndlessLazyColumn(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    listState: LazyListState = rememberLazyListState(),
    items: List<T>,
    key: ((item: T) -> Any)? = null,
    onScrolledToBottom: () -> Unit,
    itemContent: @Composable (T) -> Unit,
    loadingItemContent: @Composable () -> Unit,
) {
    val hasScrolledToBottom by remember { derivedStateOf { listState.hasScrolledToBottom() } }

    LaunchedEffect(hasScrolledToBottom) {
        if (hasScrolledToBottom && !isLoading) onScrolledToBottom()
    }

    LazyColumn(modifier = modifier, state = listState) {
        items(
            items = items,
            key = key,
        ) { item ->
            itemContent(item)
        }
        if (isLoading) {
            item { loadingItemContent() }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    GithubUsersTheme {
        ListScreen(
            userListState = fakeUserList,
            loadingState = true,
            onUserClick = {},
            onScrolledToBottom = {}
        )
    }
}

val fakeUserList = listOf(
    User(1, "John"),
    User(2, "Ann"),
    User(3, "Bob"),
    User(4, "Julia"),
    User(5, "Barbara"),
)