package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataReserva(
    @SerialName("id_reserva") val idReserva: Int = 0,
    @SerialName("folio") val folio: String = "",
    @SerialName("titulo") val titulo: String = "",
    @SerialName("autor") val autor: String = "",
    @SerialName("portada") val portada: String? = null,
    @SerialName("fecha_reserva") val fechaReserva: String = "",
    @SerialName("vigencia") val vigencia: String = ""
)
