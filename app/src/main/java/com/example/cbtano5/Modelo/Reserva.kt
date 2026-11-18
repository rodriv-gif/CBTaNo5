package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReservaRequest(
    @SerialName("tipo_usuario") val tipoUsuario: String = "",
    @SerialName("identificador") val identificador: Int = 0,
    @SerialName("id_libro") val idlibro: Int = 0,
    @SerialName("fecha_recoger") val fechaRecoger: String = ""
)


@Serializable
data class ReservaResponse(
    @SerialName("status") val estado: String = "",
    @SerialName("message") val mensaje: String = "",
    @SerialName("data") val data: dataReserva? = null
)