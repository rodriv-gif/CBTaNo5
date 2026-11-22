package com.example.cbtano5.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cbtano5.R
import com.example.cbtano5.Modelo.DetalleLibroResponse
import com.example.cbtano5.Presentador.clsDetalleLibroPresentador

class DetalleLibroActivity : AppCompatActivity() {

    private lateinit var presenter: clsDetalleLibroPresentador

    private lateinit var imgPortadaLibro: ImageView
    private lateinit var txtTituloLibro: TextView
    private lateinit var txtAutorLibro: TextView
    private lateinit var txtCategoriaLibro: TextView
    private lateinit var txtDisponibilidad: TextView
    private lateinit var txtIsbn: TextView
    private lateinit var txtUbicacion: TextView
    private lateinit var txtSinopsis: TextView

    private lateinit var btnRegresar: TextView
    private lateinit var btnReservarLibro: Button

    // Datos del usuario y del libro
    private var idLibro: Int = 0
    private var idUsuario: Int = 0
    private var nombreLector: String = ""
    private var tipoUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_libro)

        // Inicializar vistas
        imgPortadaLibro = findViewById(R.id.imgPortadaLibro)
        txtTituloLibro = findViewById(R.id.txtTituloLibro)
        txtAutorLibro = findViewById(R.id.txtAutorLibro)
        txtCategoriaLibro = findViewById(R.id.txtCategoriaLibro)
        txtDisponibilidad = findViewById(R.id.txtDisponibilidad)
        txtIsbn = findViewById(R.id.txtIsbn)
        txtUbicacion = findViewById(R.id.txtUbicacion)
        txtSinopsis = findViewById(R.id.txtSinopsis)
        btnRegresar = findViewById(R.id.btnRegresar)
        btnReservarLibro = findViewById(R.id.btnReservarLibro)

        // Inicializar presentador
        presenter = clsDetalleLibroPresentador(this)

        // Obtener datos enviados desde el catálogo
        idLibro = intent.getIntExtra("ID_LIBRO", 0)
        idUsuario = intent.getIntExtra("ID_USUARIO", 0)
        nombreLector = intent.getStringExtra("NOMBRE_LECTOR") ?: ""
        tipoUsuario = intent.getStringExtra("TIPO_USUARIO") ?: ""

        // Botón regresar
        btnRegresar.setOnClickListener {
            finish()
        }

        // Habilitar y conectar el botón Reservar
        btnReservarLibro.isEnabled = true
        btnReservarLibro.setOnClickListener {
            val intentReservar = Intent(this, ReservarLibroActivity::class.java)
            intentReservar.putExtra("ID_LIBRO", idLibro)
            intentReservar.putExtra("TITULO_LIBRO", txtTituloLibro.text.toString())
            intentReservar.putExtra("AUTOR_LIBRO", txtAutorLibro.text.toString())
            intentReservar.putExtra("CATEGORIA_LIBRO", txtCategoriaLibro.text.toString())
            intentReservar.putExtra("ID_USUARIO", idUsuario)
            intentReservar.putExtra("NOMBRE_LECTOR", nombreLector)
            intentReservar.putExtra("TIPO_USUARIO", tipoUsuario)
            startActivity(intentReservar)
        }

        // Consultar información del libro
        presenter.cargarDetalle(idLibro)
    }

    fun mostrarDetalleLibro(detalle: DetalleLibroResponse) {
        val datos = detalle.data ?: return
        val libro = datos.libro

        // Título
        txtTituloLibro.text = libro.titulo
        // Autores
        val autores = datos.autores.joinToString(", ") {
            it.nombre_completo
        }
        txtAutorLibro.text = if (autores.isEmpty()) {
            "Autor no registrado"
        } else {
            autores
        }
        // Categoría
        txtCategoriaLibro.text = libro.categoria ?: "Sin categoría"
        // Disponibilidad
        val disponibles = datos.disponibles_estimados

        txtDisponibilidad.text = "Disponible: $disponibles ejemplares"
        // ISBN
        txtIsbn.text = "ISBN: ${libro.isbn ?: "No registrado"}"
        // Ubicación
        val ubicaciones = datos.ejemplares.mapNotNull { it.localizacion }.filter { it.isNotBlank() }.distinct()

        txtUbicacion.text = if (ubicaciones.isEmpty()) {
            "Ubicación: No registrada"
        } else {
            "Ubicación: " + ubicaciones.joinToString(", ")
        }

        // Sinopsis y portada:
        txtSinopsis.text = "Sinopsis no disponible"
        imgPortadaLibro.setImageResource(android.R.drawable.ic_menu_gallery)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}
