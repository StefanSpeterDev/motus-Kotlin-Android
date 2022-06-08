package com.mpwd2.momomotus.ui.pages.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mpwd2.momomotus.data.entities.State
import com.mpwd2.momomotus.data.entities.Word

@Composable
fun InputComposable(state: State<Word>, modifier: Modifier) {

    if (state is State.Success) {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        val focusManager = LocalFocusManager.current

        TextField(
            modifier = modifier,
            value = text,
            onValueChange = { newText -> text = newText },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            textStyle = TextStyle(fontSize = 16.sp)
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