package com.example.cbtano5.Vista

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cbtano5.R
import com.example.cbtano5.Modelo.MiCuentaResponse
import com.example.cbtano5.Presentador.clsMiCuentaPresentador

class MiCuentaActivity : AppCompatActivity() {
    private lateinit var presenter: clsMiCuentaPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_cuenta)
        presenter = clsMiCuentaPresentador(this)
        val idLector = intent.getIntExtra("ID_LECTOR", 0)
        presenter.cargarMiCuenta(idLector)
    }
    fun mostrarMiCuenta(cuenta: MiCuentaResponse) {
        // acomodar los datos en la vista
    }
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}
