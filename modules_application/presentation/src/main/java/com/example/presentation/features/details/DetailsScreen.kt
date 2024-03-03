package com.example.presentation.features.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.domain.models.User
import com.example.presentation.R
import com.example.presentation.features.details.DetailsViewModel.DetailsViewModelFactory
import com.example.presentation.features.list.fakeUserList
import com.example.presentation.theme.GithubUsersTheme


@Composable
fun DetailsRoute(
    userId: Int,
    viewModel: DetailsViewModel = hiltViewModel<DetailsViewModel, DetailsViewModelFactory>
    { factory -> factory.create(userId) }
) {
    val detailsState by viewModel.detailsState.collectAsStateWithLifecycle()

    DetailsScreen(detailsState)
}

@Composable
fun DetailsScreen(detailsState: DetailsState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (detailsState) {
            is DetailsState.Success -> {
                UserDetails(detailsState.user)
            }

            is DetailsState.NotFound -> {
                Text(stringResource(R.string.user_not_found))
            }

            is DetailsState.Loading -> {
                Text(stringResource(R.string.loading))
            }
        }
    }
}

@Composable
fun UserDetails(user: User) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(100.dp),
            placeholder = painterResource(R.drawable.ic_avatar),
            model = user.avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(R.string.userid_value, user.id),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = stringResource(R.string.name_value, user.name),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = stringResource(R.string.lorem_ipsum),
            modifier = Modifier.padding(top = 16.dp)
        )
    }


}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    GithubUsersTheme {
        DetailsScreen(
            detailsState = DetailsState.Success(
                fakeUserList.first()
            )
        )
    }
}