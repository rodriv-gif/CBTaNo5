package com.example.cbtano5.Modelo

class clsModeloLibroRecibido {

    suspend fun confirmarRecibido(idLector: Int, idReserva: Int): PrestamoConfirmado {
        return RetrofitCliente.apiService.confirmarRecibido(idLector, idReserva)
    }
}
