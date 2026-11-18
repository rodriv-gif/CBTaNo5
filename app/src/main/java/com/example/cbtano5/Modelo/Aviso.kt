package com.example.cbtano5.Modelo

import com.google.gson.annotations.SerializedName

/**
 * Un aviso tal como lo regresa obtener_avisos.php.
 * Los nombres entre comillas son los campos del JSON.
 */
data class Aviso(

    @SerializedName("id_aviso")
    val idAviso: Int = 0,

    @SerializedName("id_tipo_aviso")
    val idTipoAviso: Int = 0,

    @SerializedName("titulo")
    val titulo: String,

    @SerializedName("mensaje")
    val mensaje: String,

    // "prestamo", "reserva" o null
    @SerializedName("destino")
    val destino: String? = null,

    @SerializedName("id_reserva")
    val idReserva: Int? = null,

    @SerializedName("id_prestamo")
    val idPrestamo: Int? = null,

    // Ya viene como texto: "Hoy", "Ayer" o "31 oct."
    @SerializedName("fecha")
    val fecha: String,

    @SerializedName("leido")
    val leido: Int = 0
)
