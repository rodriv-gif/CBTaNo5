package com.example.cbtano5.Vista

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.Adaptador.AdeudosAdapter
import com.example.cbtano5.Modelo.Adeudo
import com.example.cbtano5.Presentador.clsAdeudosPresentador
import com.example.cbtano5.R

class Mis_AdeudosActivity : AppCompatActivity() {

    companion object {

        // Mismo nombre que usan Inicio y Mis reservas para mandar el id del lector:
        //   val intent = Intent(this, Mis_AdeudosActivity::class.java)
        //   intent.putExtra(Mis_AdeudosActivity.EXTRA_ID_LECTOR, id)
        //   startActivity(intent)
        const val EXTRA_ID_LECTOR = "id"
    }

    private lateinit var listaAdeudos: RecyclerView
    private lateinit var textoVacio: TextView

    private val adaptador = AdeudosAdapter()

    private lateinit var presentador: clsAdeudosPresentador

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

        // Mientras llegan los datos no se muestra "No tienes adeudos".
        textoVacio.visibility = View.GONE

        presentador = clsAdeudosPresentador(this)

        val idLector = intent.getIntExtra(EXTRA_ID_LECTOR, 0)

        presentador.cargarAdeudos(idLector)
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

    /** Lo llama el presentador cuando algo falla. */
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}
