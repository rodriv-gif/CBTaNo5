package com.example.cbtano5.Modelo

class clsModeloDetalleLibro {

    suspend fun obtenerDetalleLibro(idLibro: Int): DetalleLibroResponse {

        return RetrofitCliente.apiService.obtenerDetalleLibro(idLibro)
    }
}
