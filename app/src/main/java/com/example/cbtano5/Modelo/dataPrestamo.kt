package com.example.cbtano5.Modelo

data class dataPrestamo(
    val idPrestamo: Int,
    val idLibro: Int,
    val titulo: String,
    val fechaPrestamo: String,
    val fechaLimite: String,
    val estado: String
)

data class MisPrestamosResponse(
    val success: Boolean,
    val mensaje: String,
    val prestamos: List<dataPrestamo>)
