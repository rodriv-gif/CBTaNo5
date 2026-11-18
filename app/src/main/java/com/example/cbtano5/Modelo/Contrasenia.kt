package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContraseniaRequest(
    @SerialName("tipo_usuario") val tipoUsuario: String = "",
    @SerialName("identificador") val identificador: Int = 0,
    @SerialName("contrasena_actual") val contrasenaActual: String = "",
    @SerialName("contrasena_nueva") val contrasenaNueva: String = "",
    @SerialName("contrasena_confirmar") val contrasenaConfirmar: String = ""
)

@Serializable
data class ContraseniaResponse(
    @SerialName("status") val estado: String = "",
    @SerialName("message") val mensaje: String = ""
)