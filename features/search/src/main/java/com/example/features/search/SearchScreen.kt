package com.example.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.theme.GithubUsersTheme
import com.example.domain.models.User

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val userListState by viewModel.userListState.collectAsStateWithLifecycle()

    SearchScreen(
        userList = userListState,
        onFilterTextChange = { viewModel.searchUsersBy(it) }
    )
}

@Composable
fun SearchScreen(
    userList: List<User>,
    onFilterTextChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        var text by rememberSaveable { mutableStateOf("") }

        TextField(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                onFilterTextChange(it)
                text = it
            },
            label = { Text("Enter filter name (at least 2 chars)") }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = userList,
                key = { it.id },
            ) {
                UserItem(user = it)
            }
        }
    }
}

@Composable
fun UserItem(
    user: User
) {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        val (name, country, state, divider) = createRefs()

        Text(
            modifier = Modifier.constrainAs(name) {
                top.linkTo(parent.top, margin = 12.dp)
                start.linkTo(parent.start, margin = 12.dp)
            },
            text = user.name.uppercase(),
            fontWeight = FontWeight.Medium,
            color = DarkGray,
            fontSize = 18.sp
        )

        val verticalGuideline = createGuidelineFromStart(0.5f)

        Text(
            modifier = Modifier.constrainAs(state) {
                start.linkTo(name.start)
                end.linkTo(verticalGuideline)
                top.linkTo(name.bottom, margin = 6.dp)
                width = Dimension.fillToConstraints
            },
            text = user.stateName,
            color = Gray,
            fontSize = 16.sp,
            maxLines = 1
        )

        Text(
            modifier = Modifier
                .constrainAs(country) {
                    start.linkTo(verticalGuideline, margin = 4.dp)
                    end.linkTo(parent.end, margin = 12.dp)
                    bottom.linkTo(state.bottom, margin = 2.dp)
                    width = Dimension.fillToConstraints
                },
            text = user.countryName,
            color = Gray,
            fontSize = 16.sp,
            maxLines = 1,
            textAlign = TextAlign.End,
            overflow = TextOverflow.Ellipsis
        )

        Divider(
            modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(state.bottom, margin = 10.dp)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth()
                .height(1.dp),
            color = LightGray
        )

    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    GithubUsersTheme {
        SearchScreen(
            userList = fakeUserList,
            onFilterTextChange = {}
        )
    }
}

val fakeUserList = listOf(
    User(1, "John", countryName = "United States", stateName = "California"),
    User(2, "Ann", countryName = "United States", stateName = "Florida"),
    User(3, "Bob", countryName = "United States", stateName = "Ohio"),
    User(4, "Julia", countryName = "Colombia", stateName = "Cundinamarca"),
    User(5, "Barbara", countryName = "Poland", stateName = "Mazowieckie"),
    User(
        6,
        "Clare",
        countryName = "Ecuador (Santo Domingo)",
        stateName = "Some state with long name"
    ),
)