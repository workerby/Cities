package mefimox.cities.domain.usecases

import mefimox.cities.domain.repositories.CitiesRepository

class GetCitiesList(private val citiesRepository: CitiesRepository) {
    operator fun invoke(id: Long) = citiesRepository.getCitiesList(id)
}