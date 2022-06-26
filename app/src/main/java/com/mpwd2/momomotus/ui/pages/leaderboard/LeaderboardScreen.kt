package com.mpwd2.momomotus.ui.pages.leaderboard


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mpwd2.momomotus.data.entities.State


@Composable
fun LeaderboardScreen() {


    val viewModel: LeaderboardViewModel = hiltViewModel()
    val state = viewModel.leaderboardState.collectAsState().value

    if (state is State.Success) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
        {
            items(state.data) { user ->
                CardWithContentColor(user?.pseudo, user?.email, user?.score, user?.image)
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
            .padding(all = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription = "profile pic",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, Color.Cyan, CircleShape)
            )

            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = pseudo!!,
                    color = Color.Cyan,
                    style = MaterialTheme.typography.subtitle2
                )
                // Add a vertical space between the author and message texts
                Spacer(modifier = Modifier.height(4.dp))
                Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
                    Text(
                        text = "Score : " + score.toString(),
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }

//        Row(
//
//        ) {
//            Box(
//                modifier = Modifier.fillMaxHeight(),
//                contentAlignment = Alignment.Center
//            ) {
//                if (pseudo != null) {
//                    Text(
//                        text = pseudo,
//                    )
//                }
//            }
//            Box(
//                modifier = Modifier
//                    .height(70.dp)
//                    .width(70.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Row {
//                    AsyncImage(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data(image)
//                            .crossfade(true)
//                            .build(),
//                        contentDescription = null,
//                        contentScale = ContentScale.Fit,
//                        modifier = Modifier.clip(CircleShape)
//                    )
//                }
//            }
//            Box(
//                modifier = Modifier.fillMaxHeight(),
//                contentAlignment = Alignment.Center
//            ) {
//                if (email != null) {
//                    Text(
//                        text = email,
//                    )
//                }
//            }
//        }
    }
}