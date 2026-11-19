package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Respuesta de confirmar_recibido.php cuando todo sale bien (código 200).
 * fecha_limite = "25/11/2026"
 */
@Serializable
data class PrestamoConfirmado(
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("id_prestamo") val idPrestamo: Int = 0,
    @SerialName("titulo") val titulo: String = "",
    @SerialName("fecha_limite") val fechaLimite: String = ""
)
