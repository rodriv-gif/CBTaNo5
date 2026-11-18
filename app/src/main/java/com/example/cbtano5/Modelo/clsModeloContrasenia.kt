package com.example.cbtano5.Modelo

class clsModeloContrasenia {

    suspend fun cambiarContrasenia(
        tipoUsuario: String,
        identificador: Int,
        contraseniaActual: String,
        nuevaContrasenia: String
    ): ContraseniaResponse {
        return RetrofitCliente.apiService.cambiarContrasenia(
            dataContrasenia(
                tipoUsuario,
                identificador,
                contraseniaActual,
                nuevaContrasenia)
        )
    }
}