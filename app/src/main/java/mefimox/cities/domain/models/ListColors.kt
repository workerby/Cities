package mefimox.cities.domain.models

import androidx.compose.ui.graphics.Color

data class ListColor(
    val name: String,
    val color: Color
)

val listColors = listOf(
    ListColor("Синий", Color(0xFF2B78E3)),
    ListColor("Оранжевый", Color(0xFFFF9900)),
    ListColor("Лиловый", Color(0xFFFF00FF)),
    ListColor("Зелёный", Color(0xFF4CFF00)),
    ListColor("Голубой", Color(0xFF00FFFF)),
    ListColor("Красный", Color(0xFFFF3D3D)),
)

fun getListColor(id: Int): ListColor = listColors.getOrElse(id) { listColors.first() }
