package mefimox.cities.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mefimox.cities.domain.models.CitiesList
import mefimox.cities.domain.models.City
import mefimox.cities.domain.models.emptyCitiesList
import mefimox.cities.domain.usecases.GetAllCities
import mefimox.cities.domain.usecases.UpdateInsertCitiesList
import mefimox.cities.ui.events.NewCitiesListEvents
import mefimox.cities.ui.navigation.Destination
import mefimox.cities.ui.navigation.navigationFlow
import javax.inject.Inject
import mefimox.cities.R
import mefimox.cities.ui.messages.MessageFlow

@HiltViewModel
class NewCitiesListViewModel @Inject constructor(
    private val getAllCities: GetAllCities,
    private val updateInsertCitiesList: UpdateInsertCitiesList
) : ViewModel() {
    private val _allCities = MutableStateFlow(emptyList<City>())
    val allCities = _allCities.asStateFlow()

    private val _citiesList = MutableStateFlow(emptyCitiesList())
    val citiesList = _citiesList.asStateFlow()

    private val _checkedCities = MutableStateFlow(emptySet<City>())
    val checkedCities = _checkedCities.asStateFlow()

    init {
        loadAllCities()
    }

    fun handleEvent(event: NewCitiesListEvents) {
        when (event) {
            is NewCitiesListEvents.Ok -> onOk()
            is NewCitiesListEvents.Cancel -> onCancel()
            is NewCitiesListEvents.CitiesListChange -> onCitiesListChange(event.citiesList)
            is NewCitiesListEvents.CheckChange -> onCheckedChange(event.city, event.newValue)
        }
    }

    private fun loadAllCities() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getAllCities.invoke().collect {
                    _allCities.value = it
                }
            }
        }
    }

    private suspend fun validateInput(): Boolean {
        if (citiesList.value.shortName.isEmpty()) {
            MessageFlow.emit(R.string.fill_short_name)
            return false
        }
        if (citiesList.value.fullName.isEmpty()) {
            MessageFlow.emit(R.string.fill_full_name)
            return false
        }
        if (checkedCities.value.isEmpty()) {
            MessageFlow.emit(R.string.choose_cities)
            return false
        }
        return true
    }

    private fun onOk() {
        viewModelScope.launch {
            if (!validateInput()) {
                cancel()
            }
            val list = citiesList.value.copy(
                cities = checkedCities.value.toList()
            )
            updateInsertCitiesList.invoke(list)
            navigationFlow.emit(Destination.BACK)
        }
    }

    private fun onCancel() {
        viewModelScope.launch {
            navigationFlow.emit(Destination.BACK)
        }
    }

    private fun onCitiesListChange(citiesList: CitiesList) {
        _citiesList.value = citiesList
    }

    private fun onCheckedChange(city: City, newValue: Boolean) {
        if (newValue) {
            if (_checkedCities.value.size < 5) {
                _checkedCities.value += city
            }
        } else {
            _checkedCities.value -= city
        }
    }


}