package mefimox.cities.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mefimox.cities.domain.models.CitiesList
import mefimox.cities.domain.repositories.CitiesRepository

class UpdateInsertCitiesList(private val citiesRepository: CitiesRepository) {
    suspend operator fun invoke(citiesList: CitiesList) =
        withContext(Dispatchers.IO) {
            citiesRepository.updateInsertCitiesList(citiesList)
        }
}