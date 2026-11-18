package com.example.cbtano5.Vista

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.Adaptador.AvisosAdapter
import com.example.cbtano5.Modelo.Aviso
import com.example.cbtano5.R

class Avisos_Notificaciones : AppCompatActivity() {

    private lateinit var listaAvisos: RecyclerView
    private lateinit var textoVacio: TextView

    private val adaptador = AvisosAdapter()

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

        // Datos de ejemplo (los del wireframe) para ver la pantalla.
        // Cuando conectes la API, quita esta línea y llama a
        // mostrarAvisos(lista) con lo que regrese obtener_avisos.php.
        mostrarAvisos(avisosDeEjemplo())
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

    private fun avisosDeEjemplo(): List<Aviso> {

        return listOf(

            Aviso(
                titulo = "Devoluciones",
                mensaje = "Recuerde devolver sus libros en la fecha establecida.",
                fecha = "Hoy"
            ),

            Aviso(
                titulo = "Nuevo libro",
                mensaje = "Ya está disponible “El principito” en el catálogo.",
                fecha = "Ayer"
            ),

            Aviso(
                titulo = "Horario de biblioteca",
                mensaje = "Lunes a viernes de 8:00 a 15:00 h.",
                fecha = "31 oct."
            )
        )
    }
}