package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloDetalleLibro
import com.example.cbtano5.Vista.DetalleLibroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsDetalleLibroPresentador(private val vista: DetalleLibroActivity) {
    private val modelo = clsModeloDetalleLibro()

    fun cargarDetalle(idLibro: Int) {

        if (idLibro <= 0) {
            vista.mostrarError("ID del libro no válido")
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modelo.obtenerDetalleLibro(idLibro)

                if (respuesta.success && respuesta.data != null) {
                    vista.mostrarDetalleLibro(respuesta)
                } else {
                    vista.mostrarError(respuesta.message)
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar detalle: " + e.localizedMessage)
            }
        }
    }
}
