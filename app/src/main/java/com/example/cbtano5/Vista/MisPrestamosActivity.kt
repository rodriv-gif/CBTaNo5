package com.example.cbtano5.Vista

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cbtano5.R
import com.example.cbtano5.Modelo.MisPrestamosResponse
import com.example.cbtano5.Presentador.clsMisPrestamosPresentador

class MisPrestamosActivity : AppCompatActivity() {
    private lateinit var presenter: clsMisPrestamosPresentador
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_prestamos)
        presenter = clsMisPrestamosPresentador(this)
        val idLector = intent.getIntExtra("ID_LECTOR", 0)
        presenter.cargarPrestamos(idLector)
    }
    fun mostrarPrestamos(prestamos: MisPrestamosResponse) {
        // se va a mandar los prestamos
    }
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}
