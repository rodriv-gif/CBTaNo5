package com.example.cbtano5.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.Adaptador.categoriaAdaptador
import com.example.cbtano5.Adaptador.libroAdaptador
import com.example.cbtano5.Modelo.dataCategoria
import com.example.cbtano5.Modelo.dataLibro
import com.example.cbtano5.Presentador.clsCatalogoPresentador
import com.example.cbtano5.R

class CatalogoLibrosActivity : AppCompatActivity() {

    private lateinit var presentador: clsCatalogoPresentador
    private lateinit var btnVolver: ImageButton
    private lateinit var txtBuscar: EditText
    private lateinit var btnBuscar: ImageButton
    private lateinit var rvCategorias: RecyclerView
    private lateinit var rvLibros: RecyclerView
    private lateinit var adaptadorCategorias: categoriaAdaptador
    private lateinit var adaptadorLibros: libroAdaptador

    // Variables para la barra de navegación
    private lateinit var navInicio: android.widget.LinearLayout
    private lateinit var navReservas: android.widget.LinearLayout
    private lateinit var navCuenta: android.widget.LinearLayout

    private var identificador: Int = 0
    private var nombreLector: String = ""
    private var tipoUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogo_libros)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lytRaizCatalogo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            findViewById<View>(R.id.lytEncabezadoCatalogo).setPadding(0, systemBars.top, 0, 0)
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        btnVolver = findViewById(R.id.btnVolverCatalogo)
        txtBuscar = findViewById(R.id.txtBuscarLibro)
        btnBuscar = findViewById(R.id.btnBuscarLibro)
        rvCategorias = findViewById(R.id.rvCategorias)
        rvLibros = findViewById(R.id.rvLibros)

        // Enlazamos la barra de navegación
        navInicio = findViewById(R.id.nav_inicio)
        navReservas = findViewById(R.id.nav_reservas)
        navCuenta = findViewById(R.id.nav_cuenta)

        rvCategorias.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adaptadorCategorias = categoriaAdaptador(this, emptyList())
        rvCategorias.adapter = adaptadorCategorias

        rvLibros.layoutManager = LinearLayoutManager(this)
        adaptadorLibros = libroAdaptador(this, emptyList())
        rvLibros.adapter = adaptadorLibros

        identificador = intent.getIntExtra("ID_USUARIO", 0)
        nombreLector = intent.getStringExtra("NOMBRE_LECTOR") ?: ""
        tipoUsuario = intent.getStringExtra("TIPO_USUARIO") ?: ""

        presentador = clsCatalogoPresentador(this)
        configurarListeners()
        presentador.iniciar()
    }

    private fun configurarListeners() {
        btnVolver.setOnClickListener {
            finish()
        }

        // Búsqueda en tiempo real
        txtBuscar.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {
                presentador.buscarLibros(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Clics de la barra de navegación
        navInicio.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            intent.putExtra("id", identificador)
            intent.putExtra("tipo", tipoUsuario)
            startActivity(intent)
            finish()
        }

        navReservas.setOnClickListener {
            val intent = Intent(this, MisReservasActivity::class.java)
            intent.putExtra("id", identificador)
            intent.putExtra("tipo", tipoUsuario)
            intent.putExtra("nombre", nombreLector)
            startActivity(intent)
            finish()
        }

        navCuenta.setOnClickListener {
            val intent = Intent(this, MiCuentaActivity::class.java)
            if (tipoUsuario.lowercase() == "alumno") {
                intent.putExtra("MATRICULA", identificador.toString())
            } else {
                intent.putExtra("NUMERO_CONTROL", identificador.toString())
            }
            startActivity(intent)
            finish()
        }
    }

    fun seleccionarLibro(libro: dataLibro) {
        val intent = Intent(this, DetalleLibroActivity::class.java)

        intent.putExtra("ID_LIBRO", libro.idLibro)
        intent.putExtra("PORTADA_LIBRO", libro.portada)

        intent.putExtra("ID_USUARIO", identificador)
        intent.putExtra("NOMBRE_LECTOR", nombreLector)
        intent.putExtra("TIPO_USUARIO", tipoUsuario)

        startActivity(intent)
    }

    fun seleccionarCategoria(categoria: dataCategoria) {
        txtBuscar.setText("")
        presentador.seleccionarCategoria(categoria.idCategoria)
    }

    fun mostrarMensajeExito(id: Int, titulo: String) {
        Toast.makeText(this, "ID: " + id.toString() + " - Seleccionaste: " + titulo, Toast.LENGTH_SHORT).show()
    }

    fun mostrarCategorias(categorias: List<dataCategoria>) {
        adaptadorCategorias.actualizarLista(categorias)
    }

    fun mostrarLibros(libros: List<dataLibro>) {
        adaptadorLibros.actualizarLista(libros)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}