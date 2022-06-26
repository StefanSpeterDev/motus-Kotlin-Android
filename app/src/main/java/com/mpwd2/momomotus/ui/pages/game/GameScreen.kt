package com.mpwd2.momomotus.ui.pages.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mpwd2.momomotus.R
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
                Text(text = "Mot à deviner: ")
                Text(text = "state.data.name",
                    fontWeight = FontWeight.Bold)
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

/*
    Chaque lettre = un textfield avec une maxlength = 1
    Gérer le focus
    Sur le input fonction qui regarde si on est last, sinon continuer au prochain input
    Chaque textfield possède un state (init, vide, invalide, valide, isOk, isOkButWrongPlace)
*/


