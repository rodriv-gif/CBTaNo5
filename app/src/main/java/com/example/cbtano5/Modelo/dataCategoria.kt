package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCategoria(
    @SerialName("id_categoria") val idCategoria: Int = 0,
    @SerialName("nombre") val nombre: String = ""
)
@Serializable
data class CategoriaResponse(
    @SerialName("status") val estado: String = "",
    @SerialName("message") val mensaje: String = "",
    @SerialName("data") val data: List<dataCategoria>? = emptyList()
)
