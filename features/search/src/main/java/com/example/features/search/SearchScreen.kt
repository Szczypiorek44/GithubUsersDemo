package com.example.features.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import com.example.common.R
import com.example.common.theme.BostonBlue
import com.example.common.theme.GithubUsersTheme
import com.example.domain.models.User

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val userListState by viewModel.userListState.collectAsStateWithLifecycle()

    SearchScreen(
        userList = userListState,
        onSearchCitiesClick = { viewModel.searchUsersBy(it) }
    )
}

@Composable
fun SearchScreen(
    userList: List<User>,
    onSearchCitiesClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item { TextInputRow(onSearchCitiesClick) }
        items(
            items = userList,
            key = { it.id },
        ) {
            UserItem(user = it)
        }
    }
}

@Composable
fun TextInputRow(onSearchCitiesClick: (String) -> Unit) {
    ConstraintLayout {
        var text by rememberSaveable { mutableStateOf("") }

        val (searchCitiesField, searchButton, fieldDivider, bottomDivider) = createRefs()
        BasicTextField(
            modifier = Modifier
                .constrainAs(searchCitiesField) {
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(searchButton.start, margin = 16.dp)
                    bottom.linkTo(searchButton.bottom)
                    width = Dimension.fillToConstraints
                }
                .padding(bottom = 8.dp),
            value = text,
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            onValueChange = { text = it },
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (text.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.search_cities),
                            color = Gray,
                            fontSize = 16.sp
                        )
                    }
                }
                innerTextField()
            }
        )

        Divider(
            modifier = Modifier
                .constrainAs(fieldDivider) {
                    bottom.linkTo(searchCitiesField.bottom)
                    start.linkTo(searchCitiesField.start)
                    end.linkTo(searchCitiesField.end)
                    width = Dimension.fillToConstraints
                }
                .fillMaxWidth()
                .height(1.dp),
            color = Gray
        )

        OutlinedButton(
            modifier = Modifier
                .constrainAs(searchButton) {
                    top.linkTo(parent.top, margin = 20.dp)
                    end.linkTo(parent.end, margin = 12.dp)
                    height = Dimension.fillToConstraints
                },
            border = BorderStroke(1.dp, BostonBlue),
            shape = RoundedCornerShape(5.dp),
            onClick = { onSearchCitiesClick(text) }) {
            Text(text = stringResource(id = R.string.search))
        }

        Divider(
            modifier = Modifier
                .constrainAs(bottomDivider) {
                    top.linkTo(searchButton.bottom, margin = 10.dp)
                }
                .fillMaxWidth()
                .height(1.dp),
            color = LightGray
        )
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
        val (nameText, countryText, stateText, divider) = createRefs()

        Text(
            modifier = Modifier.constrainAs(nameText) {
                top.linkTo(parent.top, margin = 12.dp)
                start.linkTo(parent.start, margin = 16.dp)
            },
            text = user.name.uppercase(),
            fontWeight = FontWeight.Medium,
            color = DarkGray,
            fontSize = 18.sp
        )

        val verticalGuideline = createGuidelineFromStart(0.5f)

        Text(
            modifier = Modifier.constrainAs(stateText) {
                start.linkTo(nameText.start)
                end.linkTo(verticalGuideline)
                top.linkTo(nameText.bottom, margin = 6.dp)
                width = Dimension.fillToConstraints
            },
            text = user.stateName,
            color = Gray,
            fontSize = 16.sp,
            maxLines = 1
        )

        Text(
            modifier = Modifier
                .constrainAs(countryText) {
                    start.linkTo(verticalGuideline, margin = 4.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    bottom.linkTo(stateText.bottom, margin = 2.dp)
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
                    top.linkTo(stateText.bottom, margin = 10.dp)
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
            onSearchCitiesClick = {}
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