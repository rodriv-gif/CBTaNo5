package com.example.cbtano5.Modelo

class clsModeloInicio {

    suspend fun obtenerInicio(numControlId: Int): dataInicio {
        return RetrofitCliente.apiService.obtenerInicio(numControlId)
    }
}
