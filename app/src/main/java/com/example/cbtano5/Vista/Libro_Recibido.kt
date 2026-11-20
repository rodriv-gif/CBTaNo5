package com.example.cbtano5.Vista

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cbtano5.R

class Libro_Recibido : AppCompatActivity() {

    companion object {

        // Opcional: si mandas un mensaje desde la pantalla anterior, reemplaza
        // al texto del wireframe.
        //   intent.putExtra(Libro_Recibido.EXTRA_MENSAJE, "Préstamo confirmado. Devuélvelo el 25/11/2026.")
        const val EXTRA_MENSAJE = "mensaje"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro_recibido)

        ajustarBarrasDelSistema(
            findViewById(R.id.raiz),
            findViewById(R.id.encabezado)
        )

        val mensajeRecibido = intent.getStringExtra(EXTRA_MENSAJE)

        if (mensajeRecibido != null) {
            findViewById<TextView>(R.id.texto_mensaje).text = mensajeRecibido
        }

        findViewById<Button>(R.id.btn_aceptar).setOnClickListener {
            finish()
        }
    }
}