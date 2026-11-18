package com.example.cbtano5

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.Adaptador.AdeudosAdapter
import com.example.cbtano5.Modelo.Adeudo
import com.example.cbtano5.Vista.ajustarBarrasDelSistema

class Mis_Adeudos : AppCompatActivity() {

    private lateinit var listaAdeudos: RecyclerView
    private lateinit var textoVacio: TextView

    private val adaptador = AdeudosAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_adeudos)

        ajustarBarrasDelSistema(
            findViewById(R.id.raiz),
            findViewById(R.id.encabezado)
        )

        listaAdeudos = findViewById(R.id.lista_adeudos)
        textoVacio = findViewById(R.id.texto_vacio)

        listaAdeudos.layoutManager = LinearLayoutManager(this)
        listaAdeudos.adapter = adaptador

        findViewById<ImageButton>(R.id.btn_volver).setOnClickListener {
            finish()
        }

        // mostrarAdeudos(respuesta.adeudos) con lo que regrese obtener_adeudos.php.
        mostrarAdeudos(adeudosDeEjemplo())
    }

    /** Muestra la lista; si viene vacía, muestra "No tienes adeudos". */
    fun mostrarAdeudos(adeudos: List<Adeudo>) {

        adaptador.actualizar(adeudos)

        if (adeudos.isEmpty()) {
            textoVacio.visibility = View.VISIBLE
            listaAdeudos.visibility = View.GONE
        } else {
            textoVacio.visibility = View.GONE
            listaAdeudos.visibility = View.VISIBLE
        }
    }

    private fun adeudosDeEjemplo(): List<Adeudo> {

        return listOf(

            Adeudo(
                tipo = "vencido",
                concepto = "Libro vencido",
                diasRetraso = 5,
                monto = 50.0,
                adeudo = "50.00 MXN"
            ),

            Adeudo(
                tipo = "reposicion",
                concepto = "Libro a reponer",
                diasRetraso = 0,
                adeudo = "Libro a reponer"
            )
        )
    }
}
