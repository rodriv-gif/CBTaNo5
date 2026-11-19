package com.example.cbtano5.Modelo

class clsModeloMisPrestamos {

    suspend fun obtenerMisPrestamos(idLector: Int): MisPrestamosResponse {
        return RetrofitCliente.apiService.obtenerMisPrestamos(idLector)
    }
}