package com.example.cbtano5.InterfaceApi

import com.example.cbtano5.Modelo.CancelarRequest
import com.example.cbtano5.Modelo.CancelarResponse
import com.example.cbtano5.Modelo.LoginRequest
import com.example.cbtano5.Modelo.LoginResponse
import com.example.cbtano5.Modelo.dataInicio
import com.example.cbtano5.Modelo.dataReserva
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    // Pantalla Iniciar sesión
    @POST("iniciar_sesion.php")
    suspend fun iniciarSesion(@Body request: LoginRequest): LoginResponse

    // Pantalla Inicio
    @GET("obtener_inicio.php")
    suspend fun obtenerInicio(@Query("id") numControlId: Int): dataInicio

    // Pantalla Mis reservas
    @GET("obtener_reservas.php")
    suspend fun obtenerReservas(@Query("id") numControlId: Int): List<dataReserva>

    @POST("cancelar_reserva.php")
    suspend fun cancelarReserva(@Body request: CancelarRequest): CancelarResponse
}