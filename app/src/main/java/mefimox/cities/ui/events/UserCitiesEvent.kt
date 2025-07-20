package mefimox.cities.ui.events

import mefimox.cities.domain.models.CitiesList

sealed class UserCitiesEvent {
    data class UpdateCities(val citiesList: CitiesList): UserCitiesEvent()
    data class ChooseUserCitiesListI(val i: Int): UserCitiesEvent()
}
