package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloLibroRecibido
import com.example.cbtano5.Vista.Libro_Recibido
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsLibroRecibidoPresentador(val vista: Libro_Recibido) {

    private val modelo = clsModeloLibroRecibido()

    fun confirmarRecibido(idLector: Int, idReserva: Int) {

        if (idLector <= 0 || idReserva <= 0) {
            vista.mostrarError("Datos de la reserva no válidos")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val prestamo = modelo.confirmarRecibido(idLector, idReserva)

                vista.mostrarConfirmacion(prestamo)

            } catch (e: Exception) {
                vista.mostrarError(
                    ErrorApi.mensaje(e, "No se pudo confirmar la recepción del libro")
                )
            }
        }
    }
}
