package com.example.cbtano5.Modelo

class clsModeloReserva {

    suspend fun crearReserva(tipoUsuario: String, identificador: Int, idLibro: Int, fechaRecoger: String): ReservaResponse {
        return RetrofitCliente.apiService.crearReserva(
            ReservaRequest(tipoUsuario, identificador, idLibro, fechaRecoger)
        )
    }
}