package com.example.cbtano5.Vista

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.Adaptador.AvisosAdapter
import com.example.cbtano5.Modelo.Aviso
import com.example.cbtano5.Presentador.clsAvisosPresentador
import com.example.cbtano5.R

class Avisos_NotificacionesActivity : AppCompatActivity() {

    companion object {

        // Mismo nombre que usan Inicio y Mis reservas para mandar el id del lector:
        //   val intent = Intent(this, Avisos_NotificacionesActivity::class.java)
        //   intent.putExtra(Avisos_NotificacionesActivity.EXTRA_ID_LECTOR, id)
        //   startActivity(intent)
        const val EXTRA_ID_LECTOR = "id"
    }

    private lateinit var listaAvisos: RecyclerView
    private lateinit var textoVacio: TextView

    private val adaptador = AvisosAdapter()

    private lateinit var presentador: clsAvisosPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avisos_notificaciones)

        ajustarBarrasDelSistema(
            findViewById(R.id.raiz),
            findViewById(R.id.encabezado)
        )

        listaAvisos = findViewById(R.id.lista_avisos)
        textoVacio = findViewById(R.id.texto_vacio)

        listaAvisos.layoutManager = LinearLayoutManager(this)
        listaAvisos.adapter = adaptador

        findViewById<ImageButton>(R.id.btn_volver).setOnClickListener {
            finish()
        }

        // Mientras llegan los datos no se muestra "No tienes notificaciones".
        textoVacio.visibility = View.GONE

        presentador = clsAvisosPresentador(this)

        val idLector = intent.getIntExtra(EXTRA_ID_LECTOR, 0)

        presentador.cargarAvisos(idLector)
    }

    /** Muestra la lista; si viene vacía, muestra "No tienes notificaciones". */
    fun mostrarAvisos(avisos: List<Aviso>) {

        adaptador.actualizar(avisos)

        if (avisos.isEmpty()) {
            textoVacio.visibility = View.VISIBLE
            listaAvisos.visibility = View.GONE
        } else {
            textoVacio.visibility = View.GONE
            listaAvisos.visibility = View.VISIBLE
        }
    }

    /** Lo llama el presentador cuando algo falla. */
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}
