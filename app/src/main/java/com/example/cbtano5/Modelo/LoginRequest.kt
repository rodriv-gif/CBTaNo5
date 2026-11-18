package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("numero_control") val numeroControl: String = "",
    @SerialName("contrasenia") val contrasenia: String = ""
)
