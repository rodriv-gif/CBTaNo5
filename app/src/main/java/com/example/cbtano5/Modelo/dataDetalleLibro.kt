
package com.example.cbtano5.Modelo

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class dataDetalleLibro(
    @SerialName("id_libro")
    val idLibro: Int,

    @SerialName("titulo_libro")
    val titulo: String,

    @SerialName("ISBN")
    val isbn: String? = null,

    val categoria: String? = null
)

@Serializable
data class dataAutorLibro(
    val id_autor: Int,
    val nombre_completo: String
)

@Serializable
data class dataEjemplarLibro(
    val id_ejemplar: Int,
    val localizacion: String? = null
)

@Serializable
data class dataDetalleLibroContenido(
    val libro: dataDetalleLibro,
    val autores: List<dataAutorLibro> = emptyList(),
    val disponibles_estimados: Int = 0,
    val ejemplares: List<dataEjemplarLibro> = emptyList()
)

@Serializable
data class DetalleLibroResponse(
    val success: Boolean,
    val message: String,
    val data: dataDetalleLibroContenido? = null
)
