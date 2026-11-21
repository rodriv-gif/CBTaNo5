package com.example.cbtano5.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
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

    private var identificador: Int = 0
    private var nombreLector: String = ""
    private var tipoUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogo_libros)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lytRaizCatalogo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnVolver = findViewById(R.id.btnVolverCatalogo)
        txtBuscar = findViewById(R.id.txtBuscarLibro)
        btnBuscar = findViewById(R.id.btnBuscarLibro)
        rvCategorias = findViewById(R.id.rvCategorias)
        rvLibros = findViewById(R.id.rvLibros)

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

        btnBuscar.setOnClickListener {
            val texto = txtBuscar.text.toString()
            presentador.buscarLibros(texto)
        }
    }

    fun seleccionarLibro(libro: dataLibro) {
        val intent = Intent(this, ReservarLibroActivity::class.java)

        intent.putExtra("ID_LIBRO", libro.idLibro)
        intent.putExtra("TITULO_LIBRO", libro.titulo)
        intent.putExtra("AUTOR_LIBRO", libro.autores)
        intent.putExtra("CATEGORIA_LIBRO", "")

        intent.putExtra("ID_USUARIO", identificador)
        intent.putExtra("NOMBRE_LECTOR", nombreLector)
        intent.putExtra("TIPO_USUARIO", tipoUsuario)

        startActivity(intent)
    }

    fun seleccionarCategoria(categoria: dataCategoria) {
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