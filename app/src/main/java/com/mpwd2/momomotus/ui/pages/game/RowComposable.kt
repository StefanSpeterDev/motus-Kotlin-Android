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
                    .border(BorderStroke(2.dp, Color.Black))
            ) {
                // Génère les input correspondant au mot choisi
                state.data.name.forEachIndexed { index, it ->
                    InputComposable(
                        letter = it.toString(),
                        state = state,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        index = index,
                    ) {
                        if(index >= viewModel.currentWord.length) {
                            viewModel.currentWord = viewModel.currentWord.plus(it);
                        }
                        println(viewModel.currentWord);

                        if(state.data.name.length - 1 == index) {
                            println("Next line")
                           viewModel.checkWord();
                        }
                    }
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