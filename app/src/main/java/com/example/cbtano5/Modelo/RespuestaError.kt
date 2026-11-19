package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Cuerpo que mandan los PHP cuando algo falla (400, 404, 409 o 500):
 * {"error":"Reserva no encontrada"}
 * Se lee desde HttpException.response()?.errorBody()
 */
@Serializable
data class RespuestaError(
    @SerialName("error") val error: String = ""
)
