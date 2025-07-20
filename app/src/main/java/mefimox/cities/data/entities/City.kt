package mefimox.cities.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class City(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val foundedIn: String
) {
    fun toModel() =
        mefimox.cities.domain.models.City(
            id = id,
            name = name,
            foundedIn = foundedIn
        )
}
