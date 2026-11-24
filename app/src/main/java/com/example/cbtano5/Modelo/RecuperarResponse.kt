package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Respuesta de recuperar_contrasenia mensaje de éxito
@Serializable
data class RecuperarResponse(
    @SerialName("mensaje") val mensaje: String = "")
