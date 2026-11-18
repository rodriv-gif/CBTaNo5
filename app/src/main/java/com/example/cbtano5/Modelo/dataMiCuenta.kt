package com.example.cbtano5.Modelo

data class dataMiCuenta(
    val idLector: Int,
    val nombre: String,
    val numeroControl: String,
    val correo: String,
    val grado: String,
    val grupo: String,
    val carrera: String
)

data class MiCuentaResponse(
    val success: Boolean,
    val mensaje: String,
    val usuario: dataMiCuenta?)
