
package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloMiCuenta
import com.example.cbtano5.Vista.MiCuentaActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsMiCuentaPresentador(private val vista: MiCuentaActivity) {

    private val modelo = clsModeloMiCuenta()
    fun cargarMiCuenta(matricula: String, numeroControl: String) {

        if (matricula.isBlank() && numeroControl.isBlank()) {
            vista.mostrarError("Identificador del lector no válido")
            return
        }
        if (matricula.isNotBlank() && numeroControl.isNotBlank()) {
            vista.mostrarError("Solo se debe enviar un identificador")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = if (matricula.isNotBlank()) {
                    modelo.obtenerMiCuentaAlumno(matricula)
                } else {
                    modelo.obtenerMiCuentaTrabajador(numeroControl)
                }
                if (respuesta.success && respuesta.data != null) {

                    vista.mostrarMiCuenta(respuesta)
                } else {

                    vista.mostrarError(respuesta.message)
                }

            } catch (e: Exception) {
                vista.mostrarError("Error al cargar cuenta: " + e.localizedMessage)
            }
        }
    }
}
