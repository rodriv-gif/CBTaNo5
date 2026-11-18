package com.example.cbtano5.Modelo

import com.google.gson.annotations.SerializedName
data class Adeudo(

    @SerializedName("id_adeudo")
    val idAdeudo: Int = 0,

    @SerializedName("tipo")
    val tipo: String = "",

    // "Libro vencido" o "Libro a reponer"
    @SerializedName("concepto")
    val concepto: String,

    @SerializedName("titulo")
    val titulo: String = "",

    // Nombre del archivo de la portada, por ejemplo "libro_1.jpg"
    @SerializedName("portada")
    val portada: String? = null,

    @SerializedName("dias_retraso")
    val diasRetraso: Int = 0,

    @SerializedName("monto")
    val monto: Double = 0.0,

    // Texto listo para mostrar: "50.00 MXN" o "Libro a reponer"
    @SerializedName("adeudo")
    val adeudo: String
)
