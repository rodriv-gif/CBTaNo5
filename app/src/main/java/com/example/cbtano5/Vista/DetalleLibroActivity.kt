package com.example.cbtano5.Vista

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    // Dirección de las portadas
    private val baseUrl = "https://biblioteccbtan5.4cuthh.com/bibliocbta5/"
    private val rutaPortadas = "upload/libros/principales/"

    // Presentador
    private lateinit var presenter: clsDetalleLibroPresentador

    // Componentes del XML
    private lateinit var imgPortadaLibro: ImageView
    private lateinit var txtTituloLibro: TextView
    private lateinit var txtAutorLibro: TextView
    private lateinit var txtCategoriaLibro: TextView
    private lateinit var txtDisponibilidad: TextView
    private lateinit var txtIsbn: TextView
    private lateinit var txtUbicacion: TextView
    private lateinit var txtSinopsis: TextView

    // Botones
    private lateinit var btnRegresar: TextView
    private lateinit var btnReservarLibro: Button

    // Barra de navegación inferior
    private lateinit var navInicio: LinearLayout
    private lateinit var navCatalogo: LinearLayout
    private lateinit var navReservas: LinearLayout
    private lateinit var navCuenta: LinearLayout

    // Datos del usuario y del libro
    private var idLibro: Int = 0
    private var idUsuario: Int = 0
    private var nombreLector: String = ""
    private var tipoUsuario: String = ""
    private var portadaLibro: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_libro)

        // Relacionar los componentes con el XML
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

        // Relacionar la barra de navegación
        navInicio = findViewById(R.id.nav_inicio)
        navCatalogo = findViewById(R.id.nav_catalogo)
        navReservas = findViewById(R.id.nav_reservas)
        navCuenta = findViewById(R.id.nav_cuenta)

        // Inicializar el presentador
        presenter = clsDetalleLibroPresentador(this)

        // Recibir información enviada desde Catálogo
        idLibro = intent.getIntExtra("ID_LIBRO", 0)
        idUsuario = intent.getIntExtra("ID_USUARIO", 0)
        nombreLector = intent.getStringExtra("NOMBRE_LECTOR") ?: ""
        tipoUsuario = intent.getStringExtra("TIPO_USUARIO") ?: ""

        // Recibir la portada directamente desde Catálogo
        portadaLibro = intent.getStringExtra("PORTADA_LIBRO")
            ?.trim() ?: ""

        // Mostrar la portada sin esperar la respuesta del API
        cargarPortada(portadaLibro)

        // Información inicial
        txtTituloLibro.text = "Cargando libro..."
        txtAutorLibro.text = "Cargando autor..."
        txtSinopsis.text = "Sinopsis no disponible"

        // Deshabilitar Reservar mientras se carga el libro
        btnReservarLibro.isEnabled = false

        // Botón regresar
        btnRegresar.setOnClickListener {
            finish()
        }

        // Abrir Inicio
        navInicio.setOnClickListener {
            abrirInicio()
        }

        // Abrir Catálogo
        navCatalogo.setOnClickListener {
            abrirCatalogo()
        }

        // Abrir Mis reservas
        navReservas.setOnClickListener {
            abrirMisReservas()
        }

        // Abrir Mi cuenta
        navCuenta.setOnClickListener {
            abrirMiCuenta()
        }

        // Consultar información del libro mediante MVP
        presenter.cargarDetalle(idLibro)
    }

    // Cargar la portada del libro
    private fun cargarPortada(portada: String) {

        val nombrePortada = portada.trim()

        // Comprobar que exista una portada
        if (nombrePortada.isBlank()) {

            imgPortadaLibro.setImageResource(
                R.drawable.cbta_ic_portada
            )

            Log.d("DetalleLibro", "No se recibió la portada")

            return
        }

        // Construir la dirección de la imagen
        val urlImagen = when {

            nombrePortada.startsWith("http://") ||
                    nombrePortada.startsWith("https://") -> {
                nombrePortada
            }

            nombrePortada.startsWith("upload/") -> {
                baseUrl + nombrePortada
            }

            nombrePortada.startsWith("/upload/") -> {
                baseUrl + nombrePortada.removePrefix("/")
            }

            else -> {
                baseUrl + rutaPortadas + nombrePortada
            }
        }

        // Comprobar la dirección en Logcat
        Log.d("DetalleLibro", "URL portada: $urlImagen")

        // Mostrar la portada con Glide
        Glide.with(this)
            .load(urlImagen)
            .placeholder(R.drawable.cbta_ic_portada)
            .error(R.drawable.cbta_ic_portada)
            .into(imgPortadaLibro)
    }

    // Mostrar información obtenida desde la API
    fun mostrarDetalleLibro(detalle: DetalleLibroResponse) {

        val datos = detalle.data ?: return
        val libro = datos.libro

        // Título del libro
        txtTituloLibro.text = libro.titulo

        // Obtener los autores del libro
        val autores = datos.autores
            .map { it.nombre_completo.trim() }
            .filter { it.isNotBlank() }
            .distinct()
            .joinToString(", ")

        // Mostrar los autores
        val autoresText = if (autores.isBlank()) {
            "Autor no registrado"
        } else {
            autores
        }

        txtAutorLibro.text = autoresText

        // Comprobar autores recibidos en Logcat
        Log.d("DetalleLibro", "Autores: $autoresText")
        Log.d("DetalleLibro", "Total autores: ${datos.autores.size}")

        // Categoría
        txtCategoriaLibro.text = libro.categoria
            ?.takeIf { it.isNotBlank() }
            ?: "Sin categoría"

        // Disponibilidad
        val disponibles = datos.disponibles_estimados

        txtDisponibilidad.text = "Disponible: $disponibles ejemplares"

        // ISBN
        txtIsbn.text = "ISBN: ${libro.isbn ?: "No registrado"}"

        // Obtener las ubicaciones de los ejemplares
        val ubicaciones = datos.ejemplares
            .mapNotNull { it.localizacion }
            .filter { it.isNotBlank() }
            .distinct()

        // Mostrar las ubicaciones
        txtUbicacion.text = if (ubicaciones.isEmpty()) {

            "Ubicación: No registrada"

        } else {

            "Ubicación: " + ubicaciones.joinToString(", ")
        }

        // Sinopsis
        txtSinopsis.text = "Sinopsis no disponible"

        // Si Catálogo no envió portada, intentar obtenerla del API
        if (portadaLibro.isBlank()) {

            portadaLibro = libro.portada?.trim() ?: ""

            cargarPortada(portadaLibro)
        }

        // Habilitar Reservar después de cargar el libro
        btnReservarLibro.isEnabled = true

        // Abrir pantalla Reservar libro
        btnReservarLibro.setOnClickListener {

            if (idUsuario > 0) {

                val intentReserva = Intent(this, ReservarLibroActivity::class.java)

                // Información del libro
                intentReserva.putExtra("ID_LIBRO", libro.idLibro)
                intentReserva.putExtra("TITULO_LIBRO", libro.titulo)
                intentReserva.putExtra("AUTOR_LIBRO", autoresText)

                intentReserva.putExtra("CATEGORIA_LIBRO", libro.categoria ?: "Sin categoría")

                intentReserva.putExtra("CATEGORIA_LIBRO", libro.categoria ?: "Sin categoría")

                // Información del usuario
                intentReserva.putExtra("ID_USUARIO", idUsuario)
                intentReserva.putExtra("NOMBRE_LECTOR", nombreLector)
                intentReserva.putExtra("TIPO_USUARIO", tipoUsuario)

                startActivity(intentReserva)

            } else {

                mostrarErrorIdentificador()
            }
        }
    }

    // Abrir pantalla Inicio
    private fun abrirInicio() {

        if (idUsuario > 0) {

            val intentNav = Intent(this, InicioActivity::class.java)

            intentNav.putExtra("id", idUsuario)
            intentNav.putExtra("tipo", tipoUsuario)
            intentNav.putExtra("nombre", nombreLector)

            startActivity(intentNav)

        } else {

            mostrarErrorIdentificador()
        }
    }

    // Abrir pantalla Catálogo
    private fun abrirCatalogo() {

        if (idUsuario > 0) {

            val intentNav = Intent(this, CatalogoLibrosActivity::class.java)

            intentNav.putExtra("ID_USUARIO", idUsuario)
            intentNav.putExtra("NOMBRE_LECTOR", nombreLector)
            intentNav.putExtra("TIPO_USUARIO", tipoUsuario)

            startActivity(intentNav)

        } else {

            mostrarErrorIdentificador()
        }
    }

    // Abrir pantalla Mis reservas
    private fun abrirMisReservas() {

        if (idUsuario > 0) {

            val intentNav = Intent(this, MisReservasActivity::class.java)

            intentNav.putExtra("id", idUsuario)
            intentNav.putExtra("tipo", tipoUsuario)
            intentNav.putExtra("nombre", nombreLector)

            startActivity(intentNav)

        } else {

            mostrarErrorIdentificador()
        }
    }

    // Abrir pantalla Mi cuenta
    private fun abrirMiCuenta() {

        if (idUsuario > 0) {

            val intentNav = Intent(this, MiCuentaActivity::class.java)

            // Enviar el identificador según el tipo de usuario
            when (tipoUsuario.trim().lowercase()) {

                "alumno" -> {
                    intentNav.putExtra("MATRICULA", idUsuario.toString())
                }

                "trabajador" -> {
                    intentNav.putExtra("NUMERO_CONTROL", idUsuario.toString())
                }

                else -> {
                    Toast.makeText(this, "Tipo de usuario no reconocido", Toast.LENGTH_SHORT).show()

                    return
                }
            }

            // Enviar los datos originales del usuario
            intentNav.putExtra("id", idUsuario)
            intentNav.putExtra("tipo", tipoUsuario)
            intentNav.putExtra("nombre", nombreLector)

            startActivity(intentNav)

        } else {

            mostrarErrorIdentificador()
        }
    }

    // Mostrar error de identificador
    private fun mostrarErrorIdentificador() {

        Toast.makeText(this, "No se encontró el identificador del usuario", Toast.LENGTH_SHORT).show()
    }

    // Mostrar errores del presentador
    fun mostrarError(mensaje: String) {

        btnReservarLibro.isEnabled = false
        txtTituloLibro.text = "No se pudo cargar el libro"
        txtAutorLibro.text = "Autor no disponible"

        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}