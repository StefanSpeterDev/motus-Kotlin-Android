package com.mpwd2.momomotus.ui.pages.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mpwd2.momomotus.data.entities.State

@Composable
fun RowComposable() {

    val viewModel: GameViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value // récupère valeur du state

    if (state is State.Success) {
        var x = 0
        while (x < 5) {
            x++;
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .border(BorderStroke(2.dp, Color.Black))
                    .padding(5.dp)
            ) {
                // Génère les input correspondant au mot choisi
                state.data.name.forEach { _ ->
                    InputComposable(
                        state = state,
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                            .aspectRatio(1f)
                            .border(BorderStroke(2.dp, Color.Red))
                            .padding(10.dp)
                    )
                }
            }
        }
    } else if (state is State.Loading) {
        CircularProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Erreur api",
                fontWeight = FontWeight.Bold
            )
        }
    }
}