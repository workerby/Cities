package mefimox.cities.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import mefimox.cities.domain.models.CitiesListData
import mefimox.cities.domain.models.getListColor
import mefimox.cities.ui.common.Carousel
import mefimox.cities.ui.common.CustomHorizontalDivider
import mefimox.cities.ui.events.UserCitiesEvent
import mefimox.cities.ui.navigation.Destination
import mefimox.cities.ui.navigation.navigationFlow
import mefimox.cities.ui.vm.MainViewModel

@Composable
fun PanelContent(vm: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceBright)
    ) {
        CustomHorizontalDivider()
        Spacer(modifier = Modifier.height(5.dp))
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(48.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(15.dp))

        val allCities by vm.allCitiesListData.collectAsState()
        val chosenListI by vm.chosenCitiesListI.collectAsState()

        val coroutineScope = rememberCoroutineScope()

        Carousel(
            chosenId = chosenListI,
            colors = allCities.map { getListColor(it.colorId).color },
            onCreate = {
                coroutineScope.launch {
                    navigationFlow.emit(Destination.NEW_LIST)
                }
            },
            onSelect = {
                vm.handleEvent(UserCitiesEvent.ChooseUserCitiesListI(it))
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        ChosenCityText(allCities, chosenListI ?: 0)
    }
}

@Composable
private fun ChosenCityText(allCities: List<CitiesListData>, i: Int) {
    if (allCities.isEmpty()) {
        return
    }
    val citiesList = allCities[i]
    Text(
        text = citiesList.fullName,
        textAlign = TextAlign.Center,
        fontSize = 15.sp,
        modifier = Modifier.fillMaxWidth()
    )
}