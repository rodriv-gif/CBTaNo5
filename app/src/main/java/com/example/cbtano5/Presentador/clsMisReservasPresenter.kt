package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.RetrofitCliente
import com.example.cbtano5.Modelo.clsModeloReservas
import com.example.cbtano5.Vista.MisReservasActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsMisReservasPresenter(val vista: MisReservasActivity) {
    private val modelo = clsModeloReservas()

    fun cargarReservas(numControlId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val reservas = modelo.obtenerReservas(numControlId)
                vista.mostrarReservas(reservas)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar reservas: " + e.localizedMessage)
            }
        }
    }

    fun cancelarReserva(idReserva: Int, numControlId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modelo.cancelarReserva(idReserva, numControlId)
                vista.mostrarMensaje(respuesta.mensaje)
                cargarReservas(numControlId)   // actualiza la lista
            } catch (e: Exception) {
                vista.mostrarError("No se pudo cancelar la reserva")
            }
        }
    }

    fun verComprobante(idReserva: Int, numControlId: Int) {
        val url = RetrofitCliente.BASE_URL + "comprobante_reserva.php?id_reserva=" + idReserva + "&id=" + numControlId
        vista.abrirComprobante(url)
    }
}