package mefimox.cities.ui.states

import mefimox.cities.domain.models.CitiesList
import mefimox.cities.domain.models.City

data class NewCitiesListState(
    val citiesList: CitiesList = CitiesList(
        shortName = "",
        fullName = "",
        colorId = 0,
        cities = emptyList()
    ),
    val checkedCities: Set<City> = emptySet()
) {
    fun toCitiesList() = citiesList.copy(
        cities = checkedCities.toList()
    )
}
