package mefimox.cities.ui.events

import mefimox.cities.domain.models.CitiesList
import mefimox.cities.domain.models.City

sealed class NewCitiesListEvents {
    object Ok: NewCitiesListEvents()
    object Cancel: NewCitiesListEvents()
    data class CitiesListChange(val citiesList: CitiesList): NewCitiesListEvents()
    data class CheckChange(val city: City, val newValue: Boolean): NewCitiesListEvents()
}