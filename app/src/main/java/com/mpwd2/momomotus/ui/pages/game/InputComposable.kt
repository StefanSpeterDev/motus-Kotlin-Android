package com.mpwd2.momomotus.ui.pages.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mpwd2.momomotus.data.entities.State
import com.mpwd2.momomotus.data.entities.Word

@Composable
fun InputComposable(
    state: State<Word>,
    modifier: Modifier,
    index: Int,
    letter: String,
    onValidating: (String) -> Unit
) {

    if (state is State.Success) {
        // letter est la première lettre du mot que l'on affiche par défaut
        // str est la string que l'user renseigne en remplissant les colonnes
        // index est la position de la lettre / son input
        // onValidating permet de
        var str = "";
        val focusManager = LocalFocusManager.current
        // Gère si c'est la première case, dans le cas où ça l'est, on la désactive
        var isEnabled = true
        if (index == 0) {
            str = letter // permet de renseigner la première lettre par défaut
            onValidating(str)
            isEnabled = false
        }
        var text by remember { mutableStateOf(TextFieldValue(str)) }

        TextField(
            modifier = modifier,
            value = text,
            enabled = isEnabled,
            placeholder = {
                if (str.isEmpty()) {
                    Text(".", style = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp))
                }
            },
            onValueChange = {
                if (it.text.length <= 1) {
                    text = it
                    focusManager.moveFocus(FocusDirection.Next)
                }

                onValidating.invoke(it.text)


            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            // TODO : ADD PREVIOUS WHEN DELETE CONTENT
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) },
            ),
            textStyle = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center),
            singleLine = true,
        )
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