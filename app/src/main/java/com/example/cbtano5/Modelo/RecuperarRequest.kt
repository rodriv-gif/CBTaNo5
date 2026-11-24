package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//datos que se mandan a recuperar contraseña
@Serializable
data class RecuperarRequest(
    @SerialName("numero_control") val numeroControl: String = "",
    @SerialName("respuesta") val respuesta: String = "",
    @SerialName("nueva_contrasenia") val nuevaContrasenia: String = "",
    @SerialName("confirmar") val confirmar: String = ""
)
