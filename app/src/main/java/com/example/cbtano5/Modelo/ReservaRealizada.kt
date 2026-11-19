package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Respuesta de obtener_reserva_realizada.php.
 * vigencia       = "20/11/2026"
 * vigencia_texto = "4 días (hasta el 20 de nov. de 2026)"
 */
@Serializable
data class ReservaRealizada(
    @SerialName("id_reserva") val idReserva: Int = 0,
    @SerialName("folio") val folio: String = "",
    @SerialName("titulo") val titulo: String = "",
    @SerialName("dias") val dias: Int = 0,
    @SerialName("vigencia") val vigencia: String = "",
    @SerialName("vigencia_texto") val vigenciaTexto: String = ""
)
