package mefimox.cities.domain.usecases

import mefimox.cities.domain.repositories.CitiesRepository

class GetAllCities(private val citiesRepository: CitiesRepository) {
    operator fun invoke() = citiesRepository.getAllCities()
}