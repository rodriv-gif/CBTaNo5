package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataAviso(
    @SerialName("id_aviso") val idAviso: Int = 0,
    @SerialName("titulo") val titulo: String = "",
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("leido") val leido: Int = 0
)
