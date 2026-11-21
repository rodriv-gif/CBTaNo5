package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloCatalogo
import com.example.cbtano5.Vista.CatalogoLibrosActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsCatalogoPresentador(private val vista: CatalogoLibrosActivity) {

    private val modelo = clsModeloCatalogo()
    private var idCategoriaActual: Int = 0
    private var textoBusquedaActual: String = ""

    fun iniciar() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modelo.obtenerCategorias()
                if (respuesta.estado == "success") {
                    vista.mostrarCategorias(respuesta.data ?: emptyList())
                }
                else {
                    vista.mostrarError(respuesta.mensaje)
                }
                cargarLibros()
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar categorías: " + e.localizedMessage)
            }
        }
    }

    fun seleccionarCategoria(idCategoria: Int) {
        idCategoriaActual = idCategoria
        cargarLibros()
    }

    fun buscarLibros(texto: String) {
        textoBusquedaActual = texto.trim()
        cargarLibros()
    }

    private fun cargarLibros() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modelo.obtenerLibros(textoBusquedaActual, idCategoriaActual)
                if (respuesta.estado == "success") { // Verifica si tu PHP devuelve "success" u "ok"
                    vista.mostrarLibros(respuesta.data ?: emptyList())
                } else {
                    vista.mostrarError(respuesta.mensaje)
                }
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar libros: " + e.localizedMessage)
            }
        }
    }
}