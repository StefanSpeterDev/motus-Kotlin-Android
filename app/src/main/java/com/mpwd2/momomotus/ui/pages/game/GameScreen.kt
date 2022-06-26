package com.mpwd2.momomotus.ui.pages.game

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
import com.mpwd2.momomotus.ui.pages.game2.GameViewModel2

@Composable
fun GameScreen() {

    /*val viewModel: GameViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value // récupère valeur du state*/
    val viewModel: GameViewModel2 = hiltViewModel()
    val state = viewModel.state.collectAsState().value
    //if(state is State.Success) {
    if(true){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Row() {
                Text(text = "Mot à deviner: poule")
                /*Text(text = state.data.name,
                    fontWeight = FontWeight.Bold)*/
            }
            Box(modifier = Modifier.padding(10.dp))
            RowComposable(
                vm = viewModel,
                nbRow = 6, word = "poule"
            )
        }
    } else if (state is State.Loading) {
        CircularProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(text = "Erreur api trying to build the game",
                fontWeight = FontWeight.Bold)
        }
    }
}

