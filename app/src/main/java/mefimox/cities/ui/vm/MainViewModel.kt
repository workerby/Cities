package mefimox.cities.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mefimox.cities.domain.models.CitiesList
import mefimox.cities.domain.models.CitiesListData
import mefimox.cities.domain.usecases.GetAllCitiesListsData
import mefimox.cities.domain.usecases.GetCitiesList
import mefimox.cities.domain.usecases.UpdateInsertCitiesList
import mefimox.cities.ui.events.UserCitiesEvent
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCitiesList: GetCitiesList,
    private val getAllCitiesListsData: GetAllCitiesListsData,
    private val updateInsertCitiesList: UpdateInsertCitiesList
) : ViewModel() {
    private val _allCitiesListData = MutableStateFlow<List<CitiesListData>>(emptyList())
    val allCitiesListData = _allCitiesListData.asStateFlow()

    private val _chosenCitiesListI = MutableStateFlow<Int?>(null)
    val chosenCitiesListI = _chosenCitiesListI.asStateFlow()

    private val _chosenCitiesList = MutableStateFlow<CitiesList?>(null)
    val chosenCitiesList = _chosenCitiesList.asStateFlow()
    private var chooseCitiesJob: Job? = null

    init {
        collectAllCitiesListsData()
    }

    fun handleEvent(event: UserCitiesEvent) {
        when (event) {
            is UserCitiesEvent.ChooseUserCitiesListI -> chooseCitiesList(event.i)
            is UserCitiesEvent.UpdateCities -> updateCities(event.citiesList)
        }
    }

    private fun collectAllCitiesListsData() {
        viewModelScope.launch {
            getAllCitiesListsData.invoke().collect {
                _allCitiesListData.value = it
                if (_chosenCitiesListI.value == null) {
                    chooseCitiesList(0)
                }
            }
        }
    }

    private fun chooseCitiesList(i: Int) {
        chooseCitiesJob?.cancel()
        chooseCitiesJob = viewModelScope.launch {
            try {
                val citiesListId = allCitiesListData.value[i].id
                _chosenCitiesListI.value = i
                getCitiesList.invoke(citiesListId).collect {
                    _chosenCitiesList.value = it
                }
            } catch (e: IndexOutOfBoundsException) {
                Log.e(this@MainViewModel::class.toString(), e.toString())
            }
        }
    }

    private fun updateCities(citiesList: CitiesList) {
        viewModelScope.launch {
            updateInsertCitiesList.invoke(citiesList)
        }
    }

}