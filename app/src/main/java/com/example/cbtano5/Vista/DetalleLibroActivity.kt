package com.example.cbtano5.Vista

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cbtano5.R
import com.example.cbtano5.Modelo.DetalleLibroResponse
import com.example.cbtano5.Presentador.clsDetalleLibroPresentador

class DetalleLibroActivity : AppCompatActivity() {

    private lateinit var presenter: clsDetalleLibroPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_libro)
        presenter = clsDetalleLibroPresentador(this)
        val idLibro = intent.getIntExtra("ID_LIBRO", 0)
        presenter.cargarDetalle(idLibro)
    }
    fun mostrarDetalleLibro(detalle: DetalleLibroResponse) {
        // colocar los datos en las vistas
    }
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}
