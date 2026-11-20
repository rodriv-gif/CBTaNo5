package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloReservaRealizada
import com.example.cbtano5.Vista.Reserva_RealizadaActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsReservaRealizadaPresentador(val vista: Reserva_RealizadaActivity) {

    private val modelo = clsModeloReservaRealizada()

    fun cargarReserva(idLector: Int, idReserva: Int) {

        if (idLector <= 0 || idReserva <= 0) {
            vista.mostrarError("Datos de la reserva no válidos")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val reserva = modelo.obtenerReservaRealizada(idLector, idReserva)

                vista.mostrarReserva(reserva)

            } catch (e: Exception) {
                vista.mostrarError(
                    ErrorApi.mensaje(e, "No se pudo cargar la reserva")
                )
            }
        }
    }
}
