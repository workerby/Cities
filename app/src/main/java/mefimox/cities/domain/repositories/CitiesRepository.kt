package mefimox.cities.domain.repositories

import kotlinx.coroutines.flow.Flow
import mefimox.cities.domain.models.CitiesList
import mefimox.cities.domain.models.CitiesListData
import mefimox.cities.domain.models.City

interface CitiesRepository {
    suspend fun prepopulate()
    fun getCitiesList(id: Long): Flow<CitiesList>
    fun getAllCitiesListsData(): Flow<List<CitiesListData>>
    fun getAllCities(): Flow<List<City>>
    suspend fun updateInsertCitiesList(citiesList: CitiesList)
}