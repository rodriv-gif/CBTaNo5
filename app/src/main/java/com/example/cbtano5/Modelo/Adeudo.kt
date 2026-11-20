package com.example.cbtano5.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Un adeudo tal como lo regresa obtener_adeudos.php.
 * tipo = "vencido", "multa" o "reposicion"
 */
@Serializable
data class Adeudo(
    @SerialName("id_adeudo") val idAdeudo: Int = 0,
    @SerialName("tipo") val tipo: String = "",
    // "Libro vencido" o "Libro a reponer"
    @SerialName("concepto") val concepto: String = "",
    @SerialName("titulo") val titulo: String = "",
    // Nombre del archivo de la portada, por ejemplo "libro_1.jpg"
    @SerialName("portada") val portada: String? = null,
    @SerialName("dias_retraso") val diasRetraso: Int = 0,
    @SerialName("monto") val monto: Double = 0.0,
    // Texto listo para mostrar: "940.00 MXN" o "Libro a reponer"
    @SerialName("adeudo") val adeudo: String = ""
)
