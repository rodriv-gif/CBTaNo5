package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloContrasenia
import com.example.cbtano5.Vista.CambiarContraseniaActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsCambiarContraseniaPresentador(private val vista: CambiarContraseniaActivity) {
    private val modelo= clsModeloContrasenia()

    fun cambiarContrasenia(tipoUsuario:String, identificador:Int, contraseniaActual:String, contraseniaNueva:String, contraseniaConfirmar:String){
        if (contraseniaActual.isEmpty()||contraseniaNueva.isEmpty()||contraseniaConfirmar.isEmpty()){
            vista.mostrarError("Todos los campos son obligatorios")
            return
        }

        if (contraseniaNueva!=contraseniaConfirmar){
            vista.mostrarError("Las contraseñas nuevas no coinciden")
            return
        }

        if (contraseniaNueva.length<8 ){
            vista.mostrarError("La contraseña debe de tener minimo 8 caracteres ")
            return
        }

        if (contraseniaNueva==contraseniaActual){
            vista.mostrarError("La nueva contraseña no puede ser igual a la contraseña actual")
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta=modelo.cambiarContrasenia(tipoUsuario, identificador, contraseniaActual, contraseniaNueva, contraseniaConfirmar)
                if (respuesta.estado=="success"){
                    vista.mostrarExito(respuesta.mensaje)
                }
                else{
                    vista.mostrarError(respuesta.mensaje)
                }
            } catch (e:Exception){
                vista.mostrarError("Error al cambiar la contraseña: "+e.localizedMessage)
            }
        }
    }

}