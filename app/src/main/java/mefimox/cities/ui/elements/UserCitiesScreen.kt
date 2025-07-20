package mefimox.cities.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mefimox.cities.domain.models.CitiesList
import mefimox.cities.domain.models.City
import mefimox.cities.ui.vm.MainViewModel
import mefimox.cities.R
import mefimox.cities.domain.models.getListColor
import mefimox.cities.ui.common.CustomHorizontalDivider
import mefimox.cities.ui.events.UserCitiesEvent
import mefimox.cities.ui.messages.messageFlow
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun UserCitiesScreen(vm: MainViewModel, onChooseListClick: () -> Unit) {
    val chosenCitiesList = vm.chosenCitiesList.collectAsState()

    Scaffold(
        topBar = { TopBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            UserCitiesList(
                citiesList = chosenCitiesList.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onReorder = { vm.handleEvent(UserCitiesEvent.UpdateCities(it)) }
            )
            BottomBar(chosenCitiesList.value, onChooseListClick)
        }
    }
}

@Composable
private fun NothingChosen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.nothing_selected))
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = stringResource(R.string.list),
            modifier = Modifier.size(45.dp)
        )
    }
}

@Composable
private fun UserCitiesList(
    citiesList: CitiesList?, modifier: Modifier = Modifier, onReorder: (CitiesList) -> Unit
) {
    Box(modifier = modifier) {
        if (citiesList == null) {
            NothingChosen()
        } else {
            CitiesList(
                cities = citiesList.cities,
                onReorder = { cities -> onReorder(citiesList.copy(cities = cities)) }
            )
        }
    }
}

@Composable
fun CitiesList(cities: List<City>, onReorder: (List<City>) -> Unit) {
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState =
        rememberReorderableLazyListState(lazyListState) { from, to ->
            onReorder(
                cities.toMutableList().apply {
                    add(to.index, removeAt(from.index))
                }
            )
        }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = lazyListState,
    ) {
        items(items = cities, key = { it.id }) {
            ReorderableItem(reorderableLazyListState, key = it.id) { isDragging ->
                Box(modifier = Modifier.draggableHandle()) {
                    ListItem(it)
                }
            }
        }
    }
}

@Composable
private fun ListItem(city: City) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = city.name, fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f))
        Text(text = city.foundedIn, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun TopBar() {
    Text(
        text = stringResource(R.string.cities),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
}

@Composable
private fun BottomBar(citiesList: CitiesList?, onChooseListClick: () -> Unit) {
    CustomHorizontalDivider()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 8.dp)
    ) {
        val coroutineScope = rememberCoroutineScope()
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = stringResource(R.string.list),
            modifier = Modifier
                .clickable {
                    coroutineScope.launch { messageFlow.emit(R.string.list) }
                }
                .size(40.dp)
        )
        val text = citiesList?.shortName ?: ""
        val color = getListColor(citiesList?.colorId ?: 0).color

        ChooseListButton(
            text = text,
            color = color,
            onClick = onChooseListClick
        )
    }
}

@Composable
private fun ChooseListButton(text: String, color: Color, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = color, shape = CircleShape),
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(text = text)
    }
}


