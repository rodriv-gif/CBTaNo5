package com.example.cbtano5.Modelo

class clsModeloMiCuenta {
    open suspend fun obtenerMiCuenta(idLector: Int): MiCuentaResponse {

        return RetrofitCliente.apiService.obtenerMiCuenta(idLector)
    }
}