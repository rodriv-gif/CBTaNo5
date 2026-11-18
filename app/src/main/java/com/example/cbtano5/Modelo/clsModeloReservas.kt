package com.example.cbtano5.Modelo

class clsModeloReservas {

    suspend fun obtenerReservas(numControlId: Int): List<dataReserva> {
        return RetrofitCliente.apiService.obtenerReservas(numControlId)
    }

    suspend fun cancelarReserva(idReserva: Int, numControlId: Int): CancelarResponse {
        return RetrofitCliente.apiService.cancelarReserva(CancelarRequest(idReserva, numControlId))
    }
}
