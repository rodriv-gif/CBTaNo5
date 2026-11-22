package com.example.cbtano5.Modelo

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive

@Serializable data class dataMiCuenta(
    val id_cuenta: Int,
    val tipo_lector: String,
    val nombre_completo: String? = null,
    val matricula: JsonPrimitive? = null,
    val numero_control: String? = null,
    val fecha_creacion: String? = null,
    val carrera: String? = null,
    val nombre_carrera: String? = null
)

@Serializable data class dataResumenCuenta(
    val total_prestamos: Int = 0,
    val prestados: Int = 0,
    val devueltos: Int = 0,
    val vencidos: Int = 0,
    val reservas_pendientes: Int = 0,
    val multas_pendientes: Int = 0,
    val total_adeudo: Double = 0.0
)

@Serializable data class dataPrestamoActivo(
    val id_prestamo: Int,
    val id_ejemplar: Int,
    val titulo_libro: String,
    val fecha_prestamo: String? = null,
    val fecha_devolucion: String? = null,
    val estado: String? = null
)

@Serializable data class dataContenidoMiCuenta(
    val cuenta: dataMiCuenta,
    val resumen: dataResumenCuenta,
    val prestamos_activos: List<dataPrestamoActivo> = emptyList()
)

@Serializable data class MiCuentaResponse(
    val success: Boolean,
    val message: String,
    val data: dataContenidoMiCuenta? = null
)
