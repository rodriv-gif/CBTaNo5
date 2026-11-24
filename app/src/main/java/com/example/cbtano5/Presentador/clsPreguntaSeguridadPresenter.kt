package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloRecuperar
import com.example.cbtano5.Vista.PreguntaSeguridadActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsPreguntaSeguridadPresenter(val vista: PreguntaSeguridadActivity) {
    private val modelo = clsModeloRecuperar()

    // Valida los datos y pide el cambio de contraseña
    fun cambiarContrasenia(numeroControl: String, respuesta: String, nueva: String, confirmar: String) {
        // Todos los campos son obligatorios
        if (respuesta.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
            vista.mostrarError("Completa todos los campos")
            return
        }
        // La contraseña nueva y su confirmación deben ser iguales
        if (nueva != confirmar) {
            vista.mostrarError("Las contraseñas no coinciden")
            return
        }
        // Mínimo 8 caracteres
        if (nueva.length < 8) {
            vista.mostrarError("La contraseña debe tener al menos 8 caracteres")
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val resultado = modelo.recuperarContrasenia(numeroControl, respuesta, nueva, confirmar)
                vista.mostrarExito(resultado.mensaje)
            } catch (e: Exception) {
                vista.mostrarError(ErrorApi.mensaje(e, "No se pudo cambiar la contraseña"))
            }
        }
    }
}