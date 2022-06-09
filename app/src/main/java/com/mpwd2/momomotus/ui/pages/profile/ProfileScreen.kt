package com.mpwd2.momomotus.ui.pages.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mpwd2.momomotus.R
import com.mpwd2.momomotus.ui.pages.leaderboard.LeaderboardViewModel

@Composable
fun ProfileScreen() {

    val viewModel: ProfileViewModel = hiltViewModel();
    val profile = viewModel.getUser();

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.stefan),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,            // crop the image if it's not a square
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)                       // clip to the circle shape
                .border(2.dp, Color.Gray, CircleShape)   // add a border (optional)
        )
        Row(
            modifier = Modifier
                .padding(top = 30.dp)
        ) {
            profile.pseudo?.let { buildTextColumn(Color.Blue, "NOM", it) }
            buildTextColumn(Color.Blue, "SCORE", profile.score.toString())
            profile.email?.let { buildTextColumn(Color.Blue, "EMAIL", it) }
        }
        buildLastGames()
    }


}

@Composable
fun buildTextColumn(color: Color, title: String, description: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(3.dp)
            .width(100.dp)
    )
    {
        Text(text = title)
        Box(
            modifier = Modifier
                .padding(vertical = 3.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = description,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                color = color,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun buildLastGames() {
    val scrollState = rememberScrollState();
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            //.verticalScroll(scrollState)

    ) {
        items(10) { index ->
            Column {
                Box(
                    modifier = Modifier
                        .border(BorderStroke(2.dp, Color.Black))
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Partie: ${index + 1}")

                }
                Box(
                    Modifier
                        .height(10.dp)
                        .background(MaterialTheme.colors.background))
            }
        }
    }
}

