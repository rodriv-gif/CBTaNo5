
package com.example.cbtano5.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cbtano5.R

class PruebasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pruebas)

        val btnDetalle = findViewById<Button>(R.id.btnDetalle)
        val btnCuenta = findViewById<Button>(R.id.btnCuenta)
        val btnPrestamos = findViewById<Button>(R.id.btnPrestamos)

        btnDetalle.setOnClickListener {

            val intentDetalle = Intent(
                this,
                DetalleLibroActivity::class.java
            )

            intentDetalle.putExtra("ID_LIBRO", 1)
            startActivity(intentDetalle)
        }


        btnCuenta.setOnClickListener {

            val intentCuenta = Intent(
                this,
                MiCuentaActivity::class.java
            )

            intentCuenta.putExtra("MATRICULA", "2026001")
            startActivity(intentCuenta)
        }

        btnPrestamos.setOnClickListener {

            val intentPrestamos = Intent(
                this,
                MisPrestamosActivity::class.java
            )

            intentPrestamos.putExtra("MATRICULA", "2026001")
            startActivity(intentPrestamos)
        }

    }
}
