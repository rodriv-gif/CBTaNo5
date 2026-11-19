package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloMisPrestamos
import com.example.cbtano5.Vista.MisPrestamosActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsMisPrestamosPresentador(private val vista: MisPrestamosActivity) {
    private val modelo = clsModeloMisPrestamos()

    fun cargarPrestamos(idLector: Int) {

        if (idLector <= 0) {
            vista.mostrarError("ID del lector no válido")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val prestamos = modelo.obtenerMisPrestamos(idLector)

                vista.mostrarPrestamos(prestamos)

            } catch (e: Exception) {
                vista.mostrarError(
                    "Error al cargar préstamos: " + e.localizedMessage
                )
            }
        }
    }
}
