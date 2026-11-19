package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloLogin
import com.example.cbtano5.Vista.LoginActivity
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsLoginPresenter(val vista: LoginActivity) {
    private val modelo = clsModeloLogin()

    fun iniciarSesion(numeroControl: String, contrasenia: String) {
        if (numeroControl.isEmpty() || contrasenia.isEmpty()) {
            vista.mostrarError("Ingresa tu número de control y contraseña")
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modelo.iniciarSesion(numeroControl, contrasenia)
                vista.abrirInicio(respuesta.id)
            } catch (e: HttpException) {
                if (e.code() == 401) {
                    vista.mostrarError("Número de control o contraseña incorrectos")
                } else {
                    vista.mostrarError("No se pudo iniciar sesión")
                }
            } catch (e: Exception) {
                vista.mostrarError("Error de conexión")
            }
        }
    }
}