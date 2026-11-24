package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloRecuperar
import com.example.cbtano5.Vista.RecuperarContraseniaActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsRecuperarContraseniaPresenter(val vista: RecuperarContraseniaActivity) {
    private val modelo = clsModeloRecuperar()
    // Busca la pregunta de seguridad del número de control
    fun buscarPregunta(numeroControl: String) {
        // Validamos que no esté vacío
        if (numeroControl.isEmpty()) {
            vista.mostrarError("Ingresa tu número de control")
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modelo.obtenerPregunta(numeroControl)
                // Si encontro la pregunta, abrimos la siguiente pantalla
                vista.abrirPregunta(numeroControl, respuesta.pregunta)
            } catch (e: Exception) {
                // Muestra el mensaje que mandó el servidor o uno por defecto
                vista.mostrarError(ErrorApi.mensaje(e, "No se pudo buscar la pregunta"))
            }
        }
    }
}