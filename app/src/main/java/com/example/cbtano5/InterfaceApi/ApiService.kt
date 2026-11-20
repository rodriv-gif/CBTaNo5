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
import com.example.cbtano5.Modelo.MisPrestamosResponse
import com.example.cbtano5.Modelo.DetalleLibroResponse
import  com.example.cbtano5.Modelo.MiCuentaResponse
import com.example.cbtano5.Modelo.Aviso
import com.example.cbtano5.Modelo.AdeudosRespuesta
import com.example.cbtano5.Modelo.ReservaRealizada
import com.example.cbtano5.Modelo.PrestamoConfirmado
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

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
///////

    // Pantalla Detalle del libro
    @GET("detalle_libro.php")
    suspend fun obtenerDetalleLibro(@Query("id_libro") idLibro: Int): DetalleLibroResponse

    // Pantalla Mis libros
    @GET("mis_libros.php")
    suspend fun obtenerMisLibros(@Query("idLector") idLector: Int): LibroResponse

    // Pantalla Mi cuenta - Alumno
    @GET("mi_cuenta.php")
    suspend fun obtenerMiCuentaAlumno(@Query("matricula") matricula: String): MiCuentaResponse

    // Pantalla Mi cuenta - Trabajador
    @GET("mi_cuenta.php")
    suspend fun obtenerMiCuentaTrabajador(@Query("numero_control") numeroControl: String): MiCuentaResponse


    // Mis préstamos - Alumno
    @GET("mis_prestamos.php")
    suspend fun obtenerMisPrestamosAlumno(@Query("matricula") matricula: String): MisPrestamosResponse

    // Mis préstamos - Trabajador
    @GET("mis_prestamos.php")
    suspend fun obtenerMisPrestamosTrabajador(@Query("numero_control") numeroControl: String): MisPrestamosResponse
    ////////

    // Pantalla Avisos (notificaciones)
    @GET("obtener_avisos.php")
    suspend fun obtenerAvisos(
        @Query("id") idLector: Int
    ): List<Aviso>

    // Pantalla Mis adeudos
    @GET("obtener_adeudos.php")
    suspend fun obtenerAdeudos(
        @Query("id") idLector: Int
    ): AdeudosRespuesta

    // Pantalla Reserva realizada
    @GET("obtener_reserva_realizada.php")
    suspend fun obtenerReservaRealizada(
        @Query("id") idLector: Int,
        @Query("id_reserva") idReserva: Int
    ): ReservaRealizada

    // Pantalla Libro recibido (este PHP lee formulario, no JSON)
    @FormUrlEncoded
    @POST("confirmar_recibido.php")
    suspend fun confirmarRecibido(
        @Field("id") idLector: Int,
        @Field("id_reserva") idReserva: Int
    ): PrestamoConfirmado
}