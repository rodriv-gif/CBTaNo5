package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloAvisos
import com.example.cbtano5.Vista.Avisos_Notificaciones
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsAvisosPresentador(val vista: Avisos_Notificaciones) {

    private val modelo = clsModeloAvisos()

    fun cargarAvisos(idLector: Int) {

        if (idLector <= 0) {
            vista.mostrarError("ID del lector no válido")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val avisos = modelo.obtenerAvisos(idLector)

                vista.mostrarAvisos(avisos)

            } catch (e: Exception) {
                vista.mostrarError(
                    ErrorApi.mensaje(e, "No se pudieron cargar los avisos")
                )
            }
        }
    }
}
