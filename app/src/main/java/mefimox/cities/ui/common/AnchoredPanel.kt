package mefimox.cities.ui.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

enum class Anchors {
    BOTTOM,
    HALF,
    TOP
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnchoredPanel(
    state: AnchoredDraggableState<Anchors>,
    backgroundContent: @Composable (BoxScope.() -> Unit),
    panelContent: @Composable (BoxScope.() -> Unit)
) {
    Box(modifier = Modifier.fillMaxSize()) {
        backgroundContent()
        Box(
            Modifier
                .fillMaxSize()
                .offset { IntOffset(0, state.requireOffset().roundToInt()) }
                .anchoredDraggable(
                    state,
                    Orientation.Vertical
                ),
            content = panelContent
        )
    }
}

