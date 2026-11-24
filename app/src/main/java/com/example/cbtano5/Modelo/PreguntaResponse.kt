package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//respuesta de obtener pregunta
@Serializable
data class PreguntaResponse(
    @SerialName("pregunta") val pregunta: String = ""
)
