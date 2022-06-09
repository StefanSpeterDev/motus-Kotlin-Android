package com.mpwd2.momomotus.ui.pages.leaderboard

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mpwd2.momomotus.data.entities.State
import com.mpwd2.momomotus.R


@Composable
fun LeaderboardScreen() {


    val viewModel: LeaderboardViewModel = hiltViewModel();
    val state = viewModel.leaderboardState.collectAsState().value;

    if (state is State.Success) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
        {
            items(state.data) { word ->
                CardWithContentColor(word?.pseudo, word?.email, word?.score, word?.image)
            }
        }
    } else if (state is State.Loading) {
        CircularProgressIndicator()
    } else {
        Text(text = "Erreur when fetching users from DB")
    }

}

@Composable
fun CardWithContentColor(pseudo: String?, email: String?, score: Int?, image: String?) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .padding(5.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(

        ) {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                if (pseudo != null) {
                    Text(
                        text = pseudo,
                    )
                }
            }
            Box(
                modifier = Modifier
                    .height(70.dp)
                    .width(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.clip(CircleShape)
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                if (email != null) {
                    Text(
                        text = email,
                    )
                }
            }
        }
    }
}