package mefimox.cities.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import mefimox.cities.data.entities.CitiesListData
import mefimox.cities.data.entities.City

@Dao
interface CitiesDao {
    @Query("SELECT COUNT(*)=0 FROM cities")
    fun isEmpty(): Boolean

    @Query("SELECT * FROM cities")
    fun getAllCities(): Flow<List<City>>

    @Query("SELECT * FROM cities WHERE id IN (:ids)")
    suspend fun getCities(ids: List<Long>): List<City>

    @Upsert
    suspend fun updateInsertCitiesList(citiesListData: CitiesListData)

    @Upsert
    suspend fun updateInsertCities(cities: List<City>)

    @Query("SELECT * FROM cities_list_data")
    fun getAllCitiesListsData(): Flow<List<CitiesListData>>

    @Query("SELECT * FROM cities_list_data WHERE id=:id")
    fun getCitiesListData(id: Long): Flow<CitiesListData>
}
