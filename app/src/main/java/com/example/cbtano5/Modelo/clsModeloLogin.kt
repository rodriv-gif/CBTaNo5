package com.example.cbtano5.Modelo

class clsModeloLogin {

    suspend fun iniciarSesion(numeroControl: String, contrasenia: String): LoginResponse {
        return RetrofitCliente.apiService.iniciarSesion(LoginRequest(numeroControl, contrasenia))
    }
}
