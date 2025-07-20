package mefimox.cities.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import mefimox.cities.R
import mefimox.cities.domain.models.City
import mefimox.cities.domain.models.getListColor
import mefimox.cities.domain.models.listColors
import mefimox.cities.ui.events.NewCitiesListEvents
import mefimox.cities.ui.vm.NewCitiesListViewModel

@Composable
fun NewCitiesListScreen() {
    val vm: NewCitiesListViewModel = hiltViewModel()

    val allCities by vm.allCities.collectAsState()
    val citiesList by vm.citiesList.collectAsState()
    val checkedCities by vm.checkedCities.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(R.string.new_cities_list),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(30.dp))

            CustomRow {
                Text(
                    text = stringResource(R.string.list_short_name),
                    modifier = Modifier.weight(1f)
                )
                CustomTextField(value = citiesList.shortName, modifier = Modifier.weight(1f)) {
                    vm.handleEvent(
                        NewCitiesListEvents.CitiesListChange(citiesList.copy(shortName = it))
                    )
                }
            }

            CustomRow {
                Text(
                    text = stringResource(R.string.list_full_name),
                    modifier = Modifier.weight(1f)
                )
                CustomTextField(value = citiesList.fullName, modifier = Modifier.weight(1f)) {
                    vm.handleEvent(
                        NewCitiesListEvents.CitiesListChange(citiesList.copy(fullName = it))
                    )
                }
            }

            CustomRow {
                Text(
                    text = stringResource(R.string.list_color),
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    CustomDropdown(citiesList.colorId) {
                        vm.handleEvent(
                            NewCitiesListEvents.CitiesListChange(citiesList.copy(colorId = it))
                        )
                    }
                }
            }

            CitiesList(
                cities = allCities,
                checkedCities = checkedCities,
                modifier = Modifier.weight(1f),
                onCheckedChange = { city, newValue ->
                    vm.handleEvent(
                        NewCitiesListEvents.CheckChange(city, newValue)
                    )
                }
            )

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp)
            ) {
                BottomButton(stringResource(R.string.cancel)) {
                    vm.handleEvent(NewCitiesListEvents.Cancel)
                }
                BottomButton(stringResource(R.string.ok)) {
                    vm.handleEvent(NewCitiesListEvents.Ok)
                }
            }

        }
    }
}

@Composable
private fun CitiesList(
    cities: List<City>,
    checkedCities: Set<City>,
    modifier: Modifier,
    onCheckedChange: (City, Boolean) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(items = cities, key = { it.id }) {
            CitiesListItem(
                city = it,
                checked = it in checkedCities,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
private fun CitiesListItem(city: City, checked: Boolean, onCheckedChange: (City, Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(city, !checked) }
            .padding(vertical = 5.dp)
    ) {
        Checkbox(checked, onCheckedChange = null)
        Spacer(Modifier.width(5.dp))
        Text(city.name)
    }
}

@Composable
private fun CustomRow(content: @Composable (RowScope.() -> Unit)) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        content = content
    )
}

@Composable
private fun CustomTextField(value: String, modifier: Modifier, onValueChange: (String) -> Unit) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .border(width = 2.dp, color = MaterialTheme.colorScheme.onSurface)
            .padding(5.dp)
    ) {
        BasicTextField(
            value = value,
            singleLine = true,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun CustomDropdown(chosenId: Int, onChooseId: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        listColors.forEachIndexed { i, listColor ->
            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ColorBox(listColor.color)
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(listColor.name)
                    }
                },
                onClick = {
                    onChooseId(i)
                    expanded = false
                }
            )
        }
    }

    val listColor = getListColor(chosenId)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { expanded = true }
            .border(width = 2.dp, color = MaterialTheme.colorScheme.onSurface)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(5.dp)
        ) {
            Text(text = listColor.name)
        }
        Box(
            contentAlignment = Alignment.Center
        ) {
            ColorBox(listColor.color)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }
    }

}

@Composable
private fun BottomButton(text: String, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable { onClick() }
            .border(width = 2.dp, color = MaterialTheme.colorScheme.onSurface)
            .padding(horizontal = 10.dp, vertical = 2.dp)
    ) {
        Text(text = text, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun ColorBox(color: Color, size: Dp = 32.dp) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(color = color)
    )
}
