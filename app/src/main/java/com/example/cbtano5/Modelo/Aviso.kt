package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Un aviso completo tal como lo regresa obtener_avisos.php.
 * (El resumen que usa Inicio es dataAviso.)
 * destino = "prestamo", "reserva" o null
 * fecha   = texto listo: "Hoy", "Ayer" o "31 oct."
 */
@Serializable
data class Aviso(
    @SerialName("id_aviso") val idAviso: Int = 0,
    @SerialName("id_tipo_aviso") val idTipoAviso: Int = 0,
    @SerialName("titulo") val titulo: String = "",
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("destino") val destino: String? = null,
    @SerialName("id_reserva") val idReserva: Int? = null,
    @SerialName("id_prestamo") val idPrestamo: Int? = null,
    @SerialName("fecha") val fecha: String = "",
    @SerialName("leido") val leido: Int = 0
)
