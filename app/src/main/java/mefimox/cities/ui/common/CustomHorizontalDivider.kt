package mefimox.cities.ui.common

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun CustomHorizontalDivider() {
    HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.onSurface)
}
