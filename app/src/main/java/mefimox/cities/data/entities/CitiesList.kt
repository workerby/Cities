package mefimox.cities.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import mefimox.cities.data.converters.fromJsonToList
import mefimox.cities.data.converters.toJson

@Entity(tableName = "cities_list_data")
data class CitiesListData(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val shortName: String,
    val fullName: String,
    val colorId: Int,
    val citiesIds: String
) {
    fun toModel() =
        mefimox.cities.domain.models.CitiesListData(
            id = id,
            shortName = shortName,
            fullName = fullName,
            colorId = colorId,
            citiesIds = citiesIds.fromJsonToList()
        )
}

fun mefimox.cities.domain.models.CitiesListData.toEntity() =
    CitiesListData(
        id = id,
        shortName = shortName,
        fullName = fullName,
        colorId = colorId,
        citiesIds = citiesIds.toJson()
    )