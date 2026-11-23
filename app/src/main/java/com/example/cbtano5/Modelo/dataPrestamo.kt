package com.example.cbtano5.Modelo

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class dataPrestamo(

    @SerialName("id_prestamo")
    val idPrestamo: Int,

    @SerialName("id_libro")
    val idLibro: Int,

    @SerialName("titulo_libro")
    val tituloLibro: String,

    @SerialName("id_estado_prestamo")
    val idEstadoPrestamo: Int,

    val estado: String,

    @SerialName("fecha_prestamo")
    val fechaPrestamo: String? = null,

    @SerialName("fecha_devolucion")
    val fechaDevolucion: String? = null,

    @SerialName("fecha_devolucion_real")
    val fechaDevolucionReal: String? = null,

    @SerialName("portada")
    val portada: String? = null,

    @SerialName("portada_libro")
    val portadaLibro: String? = null,

    @SerialName("imagen")
    val imagen: String? = null,

    @SerialName("imagen_libro")
    val imagenLibro: String? = null,

    @SerialName("libro_portada")
    val libroPortada: String? = null
)

@Serializable data class dataResumenPrestamos(val total: Int = 0, val prestados: Int = 0, val devueltos: Int = 0, val vencidos: Int = 0)

@Serializable data class dataContenidoPrestamos(
    val resumen: dataResumenPrestamos,
    val prestamos: List<dataPrestamo> = emptyList()
)

@Serializable
data class MisPrestamosResponse(
    val success: Boolean,
    val message: String,
    val data: dataContenidoPrestamos? = null
)
