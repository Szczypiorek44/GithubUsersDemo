package com.example.features.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.common.R
import com.example.common.hasScrolledToBottom
import com.example.common.theme.GithubUsersTheme
import com.example.domain.models.User
import com.example.features.list.FetchMoreState.Error
import com.example.features.list.FetchMoreState.NoMoreItems
import com.example.features.list.FetchMoreState.WaitingForMore

@Composable
fun ListRoute(
    onUserClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {
    val userListState by viewModel.userListState.collectAsStateWithLifecycle()
    val fetchMoreState by viewModel.fetchMoreState.collectAsStateWithLifecycle()

    ListScreen(
        userList = userListState,
        fetchMoreState = fetchMoreState,
        onUserClick = onUserClick,
        onSearchClick = onSearchClick,
        onScrolledToBottom = { viewModel.attemptFetchMoreUsers() },
        onRetryClick = { viewModel.attemptFetchMoreUsers() }
    )
}

@Composable
fun ListScreen(
    userList: List<User>,
    fetchMoreState: FetchMoreState,
    onUserClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onScrolledToBottom: () -> Unit,
    onRetryClick: () -> Unit
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { onSearchClick() }) {
            Icon(Icons.Filled.Search, "Floating action button.")
        }
    }) { innerPadding ->
        EndlessLazyColumn(
            modifier = Modifier.padding(innerPadding),
            items = userList,
            key = { it.id },
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            onScrolledToBottom = onScrolledToBottom,
            itemContent = { UserItem(it, onUserClick) },
            lastItemContent = { LastItem(fetchMoreState, onRetryClick) })

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
fun LastItem(fetchMoreState: FetchMoreState, onRetryClick: () -> Unit) {
    val text = when (fetchMoreState) {
        is WaitingForMore -> stringResource(R.string.waiting_fore_more_users)
        is Error -> stringResource(R.string.failed_to_download_users_tap_to_retry)
        is NoMoreItems -> stringResource(R.string.no_more_users_to_download)
    }

    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { if (fetchMoreState == Error) onRetryClick() },
        textAlign = TextAlign.Center
    )
}

@Composable
fun <T> EndlessLazyColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical,
    contentPadding: PaddingValues,
    listState: LazyListState = rememberLazyListState(),
    items: List<T>,
    key: ((item: T) -> Any)? = null,
    onScrolledToBottom: () -> Unit,
    itemContent: @Composable (T) -> Unit,
    lastItemContent: @Composable () -> Unit,
) {
    val hasScrolledToBottom by remember { derivedStateOf { listState.hasScrolledToBottom() } }

    LaunchedEffect(hasScrolledToBottom) {
        if (hasScrolledToBottom) onScrolledToBottom()
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
        verticalArrangement = verticalArrangement,
        contentPadding = contentPadding
    ) {
        items(
            items = items,
            key = key,
        ) { item ->
            itemContent(item)
        }
        item { lastItemContent() }
    }
}


@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    GithubUsersTheme {
        ListScreen(
            userList = fakeUserList,
            fetchMoreState = WaitingForMore,
            onUserClick = {},
            onSearchClick = {},
            onScrolledToBottom = {},
            onRetryClick = {}
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