package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.RespuestaError
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

/**
 *   Si no hay internet, avisa que no hay conexión.
 *   En cualquier otro caso regresa el mensaje por defecto.
 */
object ErrorApi {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun mensaje(e: Exception, porDefecto: String): String {

        return when (e) {

            is HttpException -> {

                val cuerpo = try {
                    e.response()?.errorBody()?.string()
                } catch (ex: Exception) {
                    null
                }

                val textoError = try {
                    if (cuerpo.isNullOrBlank()) {
                        ""
                    } else {
                        json.decodeFromString<RespuestaError>(cuerpo).error
                    }
                } catch (ex: Exception) {
                    ""
                }

                if (textoError.isNotBlank()) textoError else porDefecto
            }

            is IOException ->
                "Sin conexión con el servidor. Revisa tu internet."

            else ->
                porDefecto
        }
    }
}
