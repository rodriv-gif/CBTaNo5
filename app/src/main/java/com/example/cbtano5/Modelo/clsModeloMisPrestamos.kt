
package com.example.cbtano5.Modelo

class clsModeloMisPrestamos {

    suspend fun obtenerMisPrestamosAlumno(matricula: String): MisPrestamosResponse {

        return RetrofitCliente.apiService.obtenerMisPrestamosAlumno(matricula)
    }

    suspend fun obtenerMisPrestamosTrabajador(numeroControl: String): MisPrestamosResponse {

        return RetrofitCliente.apiService.obtenerMisPrestamosTrabajador(numeroControl)
    }

    // Catálogo completo (sin búsqueda ni categoría) para completar portada y autor de los préstamos
    suspend fun obtenerCatalogoLibros(): LibroResponse {

        return RetrofitCliente.apiService.obtenerLibros("", 0)
    }
}
