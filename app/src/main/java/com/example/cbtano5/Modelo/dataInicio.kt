package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataInicio(
    @SerialName("nombre") val nombre: String = "",
    @SerialName("avisos_sin_leer") val avisosSinLeer: Int = 0,
    @SerialName("avisos") val avisos: List<dataAviso> = emptyList()
)
