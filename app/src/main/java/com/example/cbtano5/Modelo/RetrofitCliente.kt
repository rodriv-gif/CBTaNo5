package com.example.cbtano5.Modelo

import android.util.Log
import com.example.cbtano5.InterfaceApi.ApiService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitCliente {
    const val BASE_URL = "https://biblioteccbtan5.4cuthh.com/bibliocbta5/" //aqui va la url del api

    private  val jsonConfig = Json{
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    // TEMPORAL DIAGNÓSTICO: imprime el JSON crudo de mis_prestamos.php en Logcat (tag DEBUG_PRESTAMOS)
    private val interceptorDiagnostico = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        val url = request.url.toString()
        if (url.contains("mis_prestamos.php")) {
            val cuerpo = response.peekBody(1_000_000).string()
            Log.d("DEBUG_PRESTAMOS", "URL: $url  (HTTP ${response.code})")
            // Logcat corta líneas de ~4000 caracteres, por eso se imprime en partes
            cuerpo.chunked(3000).forEachIndexed { i, parte ->
                Log.d("DEBUG_PRESTAMOS", "JSON parte ${i + 1}: $parte")
            }
        }
        response
    }

    // TEMPORAL DIAGNÓSTICO
    private val clienteHttp = OkHttpClient.Builder()
        .addInterceptor(interceptorDiagnostico)
        .build()

    val apiService: ApiService by lazy {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clienteHttp) // TEMPORAL DIAGNÓSTICO
            .addConverterFactory(jsonConfig.asConverterFactory(contentType))
            .build()
            .create(ApiService::class.java)
    }

}