package com.example.cbtano5.Modelo

class clsModeloReservaRealizada {

    suspend fun obtenerReservaRealizada(idLector: Int, idReserva: Int): ReservaRealizada {
        return RetrofitCliente.apiService.obtenerReservaRealizada(idLector, idReserva)
    }
}
