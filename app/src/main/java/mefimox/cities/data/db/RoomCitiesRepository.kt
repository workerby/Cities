package mefimox.cities.data.db

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mefimox.cities.data.dao.CitiesDao
import mefimox.cities.data.entities.CitiesListData
import mefimox.cities.data.entities.City
import mefimox.cities.data.entities.toEntity
import mefimox.cities.data.prepopulation.cities
import mefimox.cities.data.prepopulation.userList
import mefimox.cities.domain.models.CitiesList
import mefimox.cities.domain.repositories.CitiesRepository

@Database(entities = [City::class, CitiesListData::class], version = 1)
abstract class RoomCitiesRepository : RoomDatabase(), CitiesRepository {
    abstract fun citiesDao(): CitiesDao

    override suspend fun prepopulate() {
        val dao = citiesDao()
        if (dao.isEmpty()) {
            dao.updateInsertCities(cities)
            dao.updateInsertCitiesList(userList)
        }
    }

    private fun getCitiesListData(id: Long): Flow<mefimox.cities.domain.models.CitiesListData> {
        return citiesDao().getCitiesListData(id).map { it.toModel() }
    }

    override fun getCitiesList(id: Long): Flow<CitiesList> {
        return getCitiesListData(id).map {
            val cities = citiesDao().getCities(it.citiesIds)
            it.toCitiesList(
                it.citiesIds.mapNotNull { id -> cities.find { it.id == id }?.toModel() }
            )
        }
    }

    override fun getAllCitiesListsData(): Flow<List<mefimox.cities.domain.models.CitiesListData>> {
        return citiesDao().getAllCitiesListsData().map {
            it.map { it.toModel() }
        }
    }

    override fun getAllCities(): Flow<List<mefimox.cities.domain.models.City>> {
        return citiesDao().getAllCities().map { it.map { it.toModel() } }
    }

    override suspend fun updateInsertCitiesList(citiesList: CitiesList) {
        citiesDao().updateInsertCitiesList(
            citiesList.toCitiesListData().toEntity()
        )
    }
}
