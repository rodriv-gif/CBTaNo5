package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloAdeudos
import com.example.cbtano5.Vista.Mis_Adeudos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsAdeudosPresentador(val vista: Mis_Adeudos) {

    private val modelo = clsModeloAdeudos()

    fun cargarAdeudos(idLector: Int) {

        if (idLector <= 0) {
            vista.mostrarError("ID del lector no válido")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val respuesta = modelo.obtenerAdeudos(idLector)

                vista.mostrarAdeudos(respuesta.adeudos)

            } catch (e: Exception) {
                vista.mostrarError(
                    ErrorApi.mensaje(e, "No se pudieron cargar los adeudos")
                )
            }
        }
    }
}
