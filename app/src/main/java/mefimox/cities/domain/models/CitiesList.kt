package mefimox.cities.domain.models

data class CitiesList(
    val shortName: String,
    val fullName: String ,
    val colorId: Int,
    val cities: List<City>,
    val id: Long = 0
) {
    fun toCitiesListData() = CitiesListData(
        shortName = shortName,
        fullName = fullName,
        colorId = colorId,
        citiesIds = cities.map { it.id },
        id = id
    )
}

fun emptyCitiesList() = CitiesList(
    shortName = "",
    fullName = "",
    colorId = 0,
    cities = emptyList(),
    id = 0
)