package com.mpwd2.momomotus.ui.pages.game.composables

//import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mpwd2.momomotus.ui.pages.game.composables.SlotState.*

//@Preview
@Composable
fun WordSlotsPreview() {
    WordSlots(listOf(
        LetterSlotContent("G", GREEN),
        LetterSlotContent("Y", YELLOW),
        LetterSlotContent("B", BLACK),
        LetterSlotContent("T", TYPED),
        LetterSlotContent("E", EMPTY)
    ))
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
