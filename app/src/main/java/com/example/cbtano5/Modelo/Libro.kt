package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Libro(
    @SerialName("id_libro") val idLibro: Int = 0,
    @SerialName("titulo") val titulo: String = "",
    @SerialName("autores") val autores: String = "",
    @SerialName("portada") val portada: String = ""
)

@Serializable
data class LibroResponse(
    @SerialName("status") val estado: String = "",
    @SerialName("message") val mensaje: String = "",
    @SerialName("data") val data: List<Libro>? = emptyList()
)