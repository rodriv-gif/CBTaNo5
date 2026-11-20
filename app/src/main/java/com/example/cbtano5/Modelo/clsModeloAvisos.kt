package com.example.cbtano5.Modelo

class clsModeloAvisos {

    suspend fun obtenerAvisos(idLector: Int): List<Aviso> {
        return RetrofitCliente.apiService.obtenerAvisos(idLector)
    }
}
