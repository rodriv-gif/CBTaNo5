package com.example.cbtano5.Modelo

class clsModeloCatalogo {

    suspend fun obtenerCategorias(): CategoriaResponse {
        return RetrofitCliente.apiService.obtenerCategorias()
    }

    suspend fun obtenerLibros(buscar: String, idCategoria: Int): LibroResponse {
        return RetrofitCliente.apiService.obtenerLibros(buscar, idCategoria)
    }
}