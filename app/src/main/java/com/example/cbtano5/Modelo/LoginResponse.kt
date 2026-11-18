package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("id") val id: Int = 0,
    @SerialName("tipo") val tipo: String = "",
    @SerialName("nombre") val nombre: String = ""
)
