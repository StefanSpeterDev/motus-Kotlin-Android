
package com.mpwd2.momomotus.ui.pages.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mpwd2.momomotus.data.entities.State
import com.mpwd2.momomotus.ui.pages.game2.GameViewModel2

@Composable
fun RowComposable(nbRow: Int, word: String, vm: GameViewModel2) {

    val viewModel: GameViewModel2 = hiltViewModel()
    val state = viewModel.state.collectAsState().value // récupère valeur du state
    val context = LocalContext.current

    if (state is State.Success) {
        for (i in 1..nbRow) {
            Row(
                modifier = Modifier
                    .border(BorderStroke(2.dp, Color.Black))
                    .fillMaxWidth()
            ) {
                // Génère les input correspondant au mot choisi
                word.forEachIndexed { index, it ->
                    var color = Color.Cyan
                    var enabled = true
                    if (index == 0) {
                        enabled = false
                    }
                    var lett = ""
                    val letterTab = vm.stateTest.collectAsState().value
                    if (letterTab is State.Success) {
                        val letter = letterTab.data[i - 1][index]
                        color = letter.color
                        enabled = letter.enabled
                        lett = letter.letter
                    } else {
                        color = Color.Cyan
                    }
                    InputComposable(
                        row = i,
                        lett = lett,
                        enabled = enabled,
                        vm = vm,
                        letter = it.toString(),
                        index = index,
                        modifier = Modifier
                            .background(color)
                            .weight(1f)
                            .aspectRatio(1f)
                            .fillMaxHeight()
                            .border(1.dp, Color.Black)
                    )  {
                        if (index >= vm.currentWord.length) {
                            vm.currentWord += it
                            println(vm.currentWord)
                        } else if (index == 0) {
                            vm.currentWord = vm.motAtrouve.first().toString()
                        }

                        vm.checkWin(i - 1, context)

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
                text = "Erreur api building row",
                fontWeight = FontWeight.Bold
            )
        }
    }
}