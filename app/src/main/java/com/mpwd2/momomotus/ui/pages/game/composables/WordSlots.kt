package com.mpwd2.momomotus.ui.pages.game.composables

//import androidx.compose.desktop.ui.tooling.preview.Preview
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mpwd2.momomotus.ui.pages.game.composables.SlotState.*

//@Preview
@Composable
fun WordSlotsPreview() {
    WordSlots(listOf(
        LetterSlotContent("P", GREEN),
        LetterSlotContent("I", YELLOW),
        LetterSlotContent("A", BLACK),
        LetterSlotContent("N", TYPED),
        LetterSlotContent("E", GREEN),
        LetterSlotContent("", EMPTY)
    ))
}

@Composable
fun WordSlotsGame(leMot: String) {
    val emptyWord = List(leMot.length) { LetterSlotContent(null, TYPED) }

    emptyWord.forEachIndexed { index, letterSlot ->
        letterSlot.letter = leMot[index].toString()
        Log.d("add letter", leMot[index].toString())
    }
    WordSlots(emptyWord)
}

@Composable
fun WordSlots(
    slots: List<LetterSlotContent>,
    clickableSlots: Boolean = true,
    onLetterClicked: (Int) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        slots.forEachIndexed { index, slot ->
            LetterSlot(
                content = slot,
                isClickable = clickableSlots,
                onClicked = { onLetterClicked(index) }
            )
        }
    }
}
