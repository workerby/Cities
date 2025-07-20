package mefimox.cities.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

private val itemSize = 64.dp
private val circleSize = 32.dp
private val chosenCircleSize = 64.dp

@Composable
fun Carousel(chosenId: Int?, colors: List<Color>, onCreate: () -> Unit, onSelect: (Int) -> Unit) {
    if (colors.isEmpty()) {
        PlusItem(onClick = onCreate)
    } else {
        NotNullCarousel(chosenId ?: 0, colors, onCreate, onSelect)
    }
}

@Composable
private fun NotNullCarousel(
    chosenId: Int,
    colors: List<Color>,
    onCreate: () -> Unit,
    onSelect: (Int) -> Unit
) {
    val screenWidthPx = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    val itemSizePx = with(LocalDensity.current) {
        itemSize.toPx()
    }

    val startOffsetX = (screenWidthPx - itemSizePx) / 2 - itemSizePx

    val anchoredState = remember(colors) {
        AnchoredDraggableState(
            initialValue = chosenId,
            anchors = DraggableAnchors {
                colors.forEachIndexed { i, color ->
                    i at -itemSizePx * i
                }
            }
        )
    }

    LaunchedEffect(anchoredState.currentValue) {
        onSelect(anchoredState.currentValue)
    }

    Row(
        Modifier
            .offset {
                IntOffset((startOffsetX + anchoredState.offset).toInt(), 0)
            }
            .anchoredDraggable(
                anchoredState,
                Orientation.Horizontal
            )
    ) {
        PlusItem(onClick = onCreate)

        val coroutineScope = rememberCoroutineScope()

        colors.forEachIndexed { i, color ->
            ColorItem(
                color = color,
                chosen = i == chosenId,
                onClick = {
                    onSelect(i)
                    coroutineScope.launch {
                        anchoredState.animateTo(i)
                    }
                }
            )
        }
    }
}

@Composable
private fun PlusItem(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(itemSize)
            .clip(CircleShape)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(circleSize)
                .background(color = MaterialTheme.colorScheme.surfaceDim, shape = CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = CircleShape
                )
        )
        Text(text = "+", fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ColorItem(color: Color, chosen: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(itemSize)
            .clip(CircleShape)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(if (chosen) chosenCircleSize else circleSize)
                .background(color = color, shape = CircleShape)
        )
    }
}