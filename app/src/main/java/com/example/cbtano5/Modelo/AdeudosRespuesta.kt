package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Respuesta completa de obtener_adeudos.php:
 * {"total_adeudo": 1880, "adeudos": [ ... ]}
 */
@Serializable
data class AdeudosRespuesta(
    @SerialName("total_adeudo") val totalAdeudo: Double = 0.0,
    @SerialName("adeudos") val adeudos: List<Adeudo> = emptyList()
)
