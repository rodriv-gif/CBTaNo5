package com.example.cbtano5.Presentador

import com.example.cbtano5.Modelo.clsModeloInicio
import com.example.cbtano5.Vista.InicioActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsInicioPresenter(val vista: InicioActivity) {
    private val modelo = clsModeloInicio()

    fun cargarInicio(numControlId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val inicio = modelo.obtenerInicio(numControlId)
                vista.mostrarInicio(inicio)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar el inicio: " + e.localizedMessage)
            }
        }
    }
}