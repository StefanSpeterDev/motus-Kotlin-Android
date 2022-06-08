package com.mpwd2.momomotus.ui.pages.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowComposable() {

    var x = 0
    while(x < 5) {
        x++;
        Row(
            modifier = Modifier
                .padding(5.dp)
                .border(BorderStroke(2.dp, Color.Black))
                .padding(5.dp)
        ) {
            Text(text = "J'affiche 5 row $x")
        }
    }


}