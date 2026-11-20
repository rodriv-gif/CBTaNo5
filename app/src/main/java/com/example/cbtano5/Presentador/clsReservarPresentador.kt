package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloReserva
import com.example.cbtano5.Vista.ReservarLibroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsReservarPresentador(private val vista: ReservarLibroActivity) {
    private val modelo= clsModeloReserva()

    fun crearReserva(tipoUsuario: String, identificador:Int, idLibro:Int, fechaRecoger:String){
        if (identificador<=0 || idLibro<=0){
            vista.mostrarError("Ingresa los campos correctos")
            return
        }
        if (fechaRecoger.isEmpty()){
            vista.mostrarError("El campo viene vacio")
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta=modelo.crearReserva(tipoUsuario,identificador,idLibro,fechaRecoger)
                if (respuesta.estado == "success" && respuesta.data != null){
                    vista.reservaExitosa(respuesta.data)
                }
                else{
                    vista.mostrarError(respuesta.mensaje)
                }
            } catch (e:Exception){
                vista.mostrarError("Error al realizar la reserva: "+e.localizedMessage)
            }

        }


    }
}