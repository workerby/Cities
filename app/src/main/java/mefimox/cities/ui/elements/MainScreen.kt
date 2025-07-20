package mefimox.cities.ui.elements

import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import mefimox.cities.ui.common.AnchoredPanel
import mefimox.cities.ui.common.Anchors
import mefimox.cities.ui.vm.MainViewModel

@Composable
fun MainScreen() {
    val vm: MainViewModel = hiltViewModel()

    val coroutineScope = rememberCoroutineScope()

    val screenHeightPx = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }

    val anchoredState = remember {
        AnchoredDraggableState(
            initialValue = Anchors.BOTTOM,
            anchors = DraggableAnchors {
                Anchors.BOTTOM at screenHeightPx
                Anchors.HALF at screenHeightPx * 0.35f
                Anchors.TOP at 0f
            }
        )
    }

    AnchoredPanel(
        state = anchoredState,
        backgroundContent = {
            UserCitiesScreen(vm) {
                coroutineScope.launch {
                    anchoredState.animateTo(Anchors.HALF)
                }
            }
        },
        panelContent = {
            PanelContent(vm)
        }
    )
}