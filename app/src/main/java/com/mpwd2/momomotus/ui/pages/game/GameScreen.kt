package com.mpwd2.momomotus.ui.pages.game

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mpwd2.momomotus.data.entities.State
import com.mpwd2.momomotus.ui.pages.game.composables.WordSlotsGame
import com.mpwd2.momomotus.ui.pages.game.composables.WordSlotsPreview

@Composable
fun GameScreen() {

    val viewModel: GameViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value // récupère valeur du state

    if (state is State.Success) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .animateContentSize()
        ) {
            WordSlotsGame(leMot = state.data.name)
            WordSlotsPreview()
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Row() {
                Text(text = "Mot à deviner: ")
                Text(
                    text = state.data.name,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(modifier = Modifier.padding(10.dp))
            BuildGame()
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

/*
    Chaque lettre = un textfield avec une maxlength = 1
    Gérer le focus
    Sur le input fonction qui regarde si on est last, sinon continuer au prochain input
    Chaque textfield possède un state (init, vide, invalide, valide, isOk, isOkButWrongPlace)
*/

@Composable
fun BuildGame() {
    RowComposable()
}
