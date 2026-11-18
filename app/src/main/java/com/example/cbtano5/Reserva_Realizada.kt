package com.example.cbtano5

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cbtano5.Vista.ajustarBarrasDelSistema

class Reserva_Realizada : AppCompatActivity() {

    companion object {
        const val EXTRA_TITULO = "titulo"
        const val EXTRA_VIGENCIA = "vigencia_texto"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserva_realizada)

        ajustarBarrasDelSistema(
            findViewById(R.id.raiz),
            findViewById(R.id.encabezado)
        )

        // Si no llegan datos, se usan los del wireframe para poder ver la pantalla sola.
        val titulo = intent.getStringExtra(EXTRA_TITULO)
            ?: "Cien años de soledad"

        val vigencia = intent.getStringExtra(EXTRA_VIGENCIA)
            ?: "3 días (hasta el 05 de nov. de 2025)"

        findViewById<TextView>(R.id.texto_mensaje).text =
            getString(R.string.cbta_reserva_mensaje, titulo)

        findViewById<TextView>(R.id.texto_vigencia).text = vigencia

        findViewById<Button>(R.id.btn_aceptar).setOnClickListener {
            finish()
        }
    }
}
