package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CancelarResponse(
    @SerialName("mensaje") val mensaje: String = ""
)
