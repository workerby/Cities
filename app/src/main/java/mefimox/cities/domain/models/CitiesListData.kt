package mefimox.cities.domain.models

data class CitiesListData(
    val shortName: String,
    val fullName: String,
    val colorId: Int,
    val citiesIds: List<Long>,
    val id: Long = 0
) {
    fun toCitiesList(cities: List<City>) = CitiesList(
        shortName = shortName,
        fullName = fullName,
        colorId = colorId,
        cities = cities,
        id = id
    )
}