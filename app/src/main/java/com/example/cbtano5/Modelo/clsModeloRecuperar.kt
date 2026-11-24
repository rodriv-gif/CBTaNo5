package com.example.cbtano5.Modelo

class clsModeloRecuperar {
    // Pide la pregunta de seguridad de la cuenta
    suspend fun obtenerPregunta(numeroControl: String): PreguntaResponse {
        return RetrofitCliente.apiService.obtenerPregunta(numeroControl)
    }

    // Manda la respuesta y la contraseña nueva para cambiarla
    suspend fun recuperarContrasenia(numeroControl: String, respuesta: String, nueva: String, confirmar: String): RecuperarResponse {
        return RetrofitCliente.apiService.recuperarContrasenia(
            RecuperarRequest(numeroControl, respuesta, nueva, confirmar)
        )
    }
}