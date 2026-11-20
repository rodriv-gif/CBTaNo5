package com.example.cbtano5.Modelo

class clsModeloMiCuenta {

    suspend fun obtenerMiCuentaAlumno(matricula: String): MiCuentaResponse {

        return RetrofitCliente.apiService.obtenerMiCuentaAlumno(matricula)
    }

    suspend fun obtenerMiCuentaTrabajador(numeroControl: String): MiCuentaResponse {

        return RetrofitCliente.apiService.obtenerMiCuentaTrabajador(numeroControl)
    }
}
