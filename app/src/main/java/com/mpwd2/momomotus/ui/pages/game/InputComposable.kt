package com.mpwd2.momomotus.ui.pages.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mpwd2.momomotus.ui.pages.game2.GameViewModel2

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputComposable(
    row: Int,
    lett: String,
    enabled: Boolean,
    vm: GameViewModel2,
    letter: String,
    index: Int,
    modifier: Modifier,
    onValidating: (String) -> Unit,
) {

    var char = if (index == 0) letter else lett
    var text by remember { mutableStateOf(TextFieldValue(char)) }
    val readonly = if (index == 0) true else false
    val maxChar = 1
    var vide: Boolean by remember { mutableStateOf(true) }

    if (index == 0) onValidating(letter)

    //if (state is State.Success) {
    // letter est la première lettre du mot que l'on affiche par défaut
    // str est la string que l'user renseigne en remplissant les colonnes
    // index est la position de la lettre / son input
    // onValidating permet de
    var str = "";
    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier,
    ) {
        Box(
        ) {

        }
        TextField(
            modifier = Modifier.onKeyEvent {
                if (it.key == Key.Backspace) {
                    focusManager.moveFocus(FocusDirection.Previous)
                }
                true
            },
            value = text,
            readOnly = readonly,
            enabled = enabled,
            placeholder = {
                if (str.isEmpty()) {
                    Text(".", style = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp))
                }
            },
            onValueChange = {
                onValidating.invoke(it.text)
                if (it.text.length == maxChar && index != 0) {
                    text = it
                    focusManager.moveFocus(FocusDirection.Next)
                } else if (it.text.isEmpty()) {
                    vide = true
                    text = it
                    vm.currentWord += it
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                disabledTextColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            // TODO : ADD PREVIOUS WHEN DELETE CONTENT
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) },
            ),
            textStyle = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center),
            singleLine = true,
        )
    }
    /*} else if (state is State.Loading) {
        CircularProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Erreur api trying to build input",
                fontWeight = FontWeight.Bold
            )
        }
    }*/
}

