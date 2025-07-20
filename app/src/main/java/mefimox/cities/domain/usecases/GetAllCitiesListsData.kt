package mefimox.cities.domain.usecases

import mefimox.cities.domain.repositories.CitiesRepository

class GetAllCitiesListsData(private val citiesRepository: CitiesRepository) {
    operator fun invoke() = citiesRepository.getAllCitiesListsData()
}