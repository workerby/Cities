package mefimox.cities.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mefimox.cities.data.db.RoomCitiesRepository
import mefimox.cities.domain.repositories.CitiesRepository
import mefimox.cities.domain.usecases.GetAllCities
import mefimox.cities.domain.usecases.GetAllCitiesListsData
import mefimox.cities.domain.usecases.GetCitiesList
import mefimox.cities.domain.usecases.UpdateInsertCitiesList
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCitiesRepository(app: Application): CitiesRepository {
        return Room
            .databaseBuilder(app, RoomCitiesRepository::class.java, "cities_database.db")
            .build()
            .apply {
                CoroutineScope(Dispatchers.IO).launch { prepopulate() }
            }
    }

    @Provides
    @Singleton
    fun provideGetAllCities(repository: CitiesRepository) = GetAllCities(repository)

    @Provides
    @Singleton
    fun provideGetListOfCitiesLists(repository: CitiesRepository) =
        GetAllCitiesListsData(repository)

    @Provides
    @Singleton
    fun provideGetUserCitiesList(repository: CitiesRepository) = GetCitiesList(repository)

    @Provides
    @Singleton
    fun provideUpdateInsertCitiesList(repository: CitiesRepository) =
        UpdateInsertCitiesList(repository)

}