package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloMiCuenta
import com.example.cbtano5.Vista.MiCuentaActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsMiCuentaPresentador(private val vista: MiCuentaActivity) {
    private val modelo = clsModeloMiCuenta()

    fun cargarMiCuenta(idLector: Int) {

        if (idLector <= 0) {
            vista.mostrarError("ID del lector no válido")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val cuenta = modelo.obtenerMiCuenta(idLector)

                vista.mostrarMiCuenta(cuenta)

            } catch (e: Exception) {
                vista.mostrarError(
                    "Error al cargar cuenta: " + e.localizedMessage
                )
            }
        }
    }
}
