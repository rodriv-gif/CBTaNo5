package com.example.cbtano5.InterfaceApi

import com.example.cbtano5.Modelo.CancelarRequest
import com.example.cbtano5.Modelo.CancelarResponse
import com.example.cbtano5.Modelo.CategoriaResponse
import com.example.cbtano5.Modelo.ContraseniaResponse
import com.example.cbtano5.Modelo.LibroResponse
import com.example.cbtano5.Modelo.LoginRequest
import com.example.cbtano5.Modelo.LoginResponse
import com.example.cbtano5.Modelo.ReservaRequest
import com.example.cbtano5.Modelo.ReservaResponse
import com.example.cbtano5.Modelo.dataContrasenia
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

    //Pantalla de catalogo
    @GET("obtener_categorias.php")
    suspend fun obtenerCategorias(): CategoriaResponse

    @GET("obtener_libros.php")
    suspend fun obtenerLibros(
        @Query("buscar") buscar: String,
        @Query("id_categoria") idCategoria: Int
    ): LibroResponse

    //Pantalla de reservar
    @POST("crear_reserva.php")
    suspend fun crearReserva(@Body reserva: ReservaRequest): ReservaResponse
    //Pantalla de cambiar contraseña
    @POST("cambiar_contrasenia.php")
    suspend fun cambiarContrasenia(@Body peticion: dataContrasenia): ContraseniaResponse

}