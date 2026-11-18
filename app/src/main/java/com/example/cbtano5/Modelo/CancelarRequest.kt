package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CancelarRequest(
    @SerialName("id_reserva") val idReserva: Int = 0,
    @SerialName("id") val id: Int = 0
)
