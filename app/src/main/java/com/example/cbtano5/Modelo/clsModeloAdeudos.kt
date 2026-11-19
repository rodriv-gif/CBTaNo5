package com.example.cbtano5.Modelo

class clsModeloAdeudos {

    suspend fun obtenerAdeudos(idLector: Int): AdeudosRespuesta {
        return RetrofitCliente.apiService.obtenerAdeudos(idLector)
    }
}
