package com.example.cbtano5.Modelo

data class dataDetalleLibro(
    val idLibro: Int,
    val titulo: String,
    val isbn: String,
    val disponibilidad: String,
    val ubicacion: String,
    val sinopsis: String
)

data class DetalleLibroResponse(
    val success: Boolean,
    val mensaje: String,
    val libro: dataDetalleLibro?
)
