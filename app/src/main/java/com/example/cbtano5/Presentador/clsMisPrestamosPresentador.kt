package com.example.cbtano5.Presentador

import android.util.Log
import com.example.cbtano5.Modelo.clsModeloMisPrestamos
import com.example.cbtano5.Modelo.dataLibro
import com.example.cbtano5.Vista.MisPrestamosActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsMisPrestamosPresentador(private val vista: MisPrestamosActivity) {

    private val modelo = clsModeloMisPrestamos()
    fun cargarPrestamos(
        matricula: String,
        numeroControl: String
    ) {
        if (matricula.isBlank() && numeroControl.isBlank()) {
            vista.mostrarError("Identificador del lector no válido")
            return
        }
        if (matricula.isNotBlank() && numeroControl.isNotBlank()) {
            vista.mostrarError("Solo se permite un identificador")
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = if (matricula.isNotBlank()) {

                    modelo.obtenerMisPrestamosAlumno(matricula)

                } else {
                    modelo.obtenerMisPrestamosTrabajador(numeroControl)
                }
                if (respuesta.success && respuesta.data != null) {

                    // Catálogo para completar portada y autor.
                    // Si falla, los préstamos se muestran igual (mapa vacío).
                    val librosPorId: Map<Int, dataLibro> = try {
                        modelo.obtenerCatalogoLibros().data.orEmpty().associateBy { it.idLibro }
                    } catch (e: Exception) {
                        Log.d("DEBUG_PRESTAMOS", "No se pudo cargar el catálogo: ${e.localizedMessage}") // TEMPORAL DIAGNÓSTICO
                        emptyMap()
                    }

                    vista.mostrarPrestamos(respuesta, librosPorId)
                } else {
                    vista.mostrarError(respuesta.message)
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar préstamos: " + e.localizedMessage
                )
            }
        }
    }
}

