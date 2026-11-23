package com.example.cbtano5.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cbtano5.R
import com.example.cbtano5.Modelo.DetalleLibroResponse
import com.example.cbtano5.Presentador.clsDetalleLibroPresentador

class DetalleLibroActivity : AppCompatActivity() {

    private val baseUrl = "https://biblioteccbtan5.4cuthh.com/bibliocbta5/"
    private val rutaPortadas = "upload/libros/principales/"

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

    private lateinit var navInicio: LinearLayout
    private lateinit var navCatalogo: LinearLayout
    private lateinit var navReservas: LinearLayout
    private lateinit var navCuenta: LinearLayout

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

        navInicio = findViewById(R.id.nav_inicio)
        navCatalogo = findViewById(R.id.nav_catalogo)
        navReservas = findViewById(R.id.nav_reservas)
        navCuenta = findViewById(R.id.nav_cuenta)

        navInicio.setOnClickListener {
            val intentNav = Intent(this, InicioActivity::class.java)
            intentNav.putExtra("id", idUsuario)
            intentNav.putExtra("tipo", tipoUsuario)
            startActivity(intentNav)
        }

        navCatalogo.setOnClickListener {
            val intentNav = Intent(this, CatalogoLibrosActivity::class.java)
            intentNav.putExtra("ID_USUARIO", idUsuario)
            intentNav.putExtra("NOMBRE_LECTOR", nombreLector)
            intentNav.putExtra("TIPO_USUARIO", tipoUsuario)
            startActivity(intentNav)
        }

        navReservas.setOnClickListener {
            val intentNav = Intent(this, MisReservasActivity::class.java)
            intentNav.putExtra("id", idUsuario)
            intentNav.putExtra("tipo", tipoUsuario)
            intentNav.putExtra("nombre", nombreLector)
            startActivity(intentNav)
        }

        navCuenta.setOnClickListener {
            val intentNav = Intent(this, MiCuentaActivity::class.java)
            if (tipoUsuario.lowercase() == "alumno" || tipoUsuario.isEmpty()) {
                intentNav.putExtra("MATRICULA", idUsuario.toString())
            } else {
                intentNav.putExtra("NUMERO_CONTROL", idUsuario.toString())
            }
            intentNav.putExtra("id", idUsuario)
            startActivity(intentNav)
        }

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
        
        val portada = (intent.getStringExtra("PORTADA_LIBRO") ?: libro.portada)?.trim() ?: ""
        val urlImagen = when {
            portada.startsWith("http") -> portada
            portada.startsWith("upload/") -> baseUrl + portada
            else -> baseUrl + rutaPortadas + portada
        }

        Glide.with(this)
            .load(urlImagen)
            .placeholder(R.drawable.cbta_ic_portada)
            .error(R.drawable.cbta_ic_portada)
            .into(imgPortadaLibro)

        btnReservarLibro.isEnabled = true
        btnReservarLibro.setOnClickListener {
            val intentReserva = Intent(this, ReservarLibroActivity::class.java)
            
            intentReserva.putExtra("ID_LIBRO", libro.idLibro)
            intentReserva.putExtra("TITULO_LIBRO", libro.titulo)
            
            val autoresText = if (autores.isEmpty()) "Autor no registrado" else autores
            intentReserva.putExtra("AUTOR_LIBRO", autoresText)
            intentReserva.putExtra("CATEGORIA_LIBRO", libro.categoria ?: "Sin categoría")

            intentReserva.putExtra("ID_USUARIO", idUsuario)
            intentReserva.putExtra("NOMBRE_LECTOR", nombreLector)
            intentReserva.putExtra("TIPO_USUARIO", tipoUsuario)
            
            startActivity(intentReserva)
        }
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}
