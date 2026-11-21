package com.example.cbtano5.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.Adaptador.AvisosAdaptador
import com.example.cbtano5.Modelo.dataInicio
import com.example.cbtano5.Presentador.clsInicioPresenter
import com.example.cbtano5.R

// Pantalla de Inicio del lector
class InicioActivity : AppCompatActivity() {

    // definimos los componentes
    lateinit var txtSaludo: TextView
    lateinit var cardCatalogo: LinearLayout
    lateinit var cardPrestamos: LinearLayout
    lateinit var cardReservas: LinearLayout
    lateinit var rvAvisos: RecyclerView
    lateinit var navReservas: LinearLayout
    lateinit var navCuenta: LinearLayout
    lateinit var ivAvisos: ImageView
    lateinit var navCatalogo: LinearLayout
    lateinit var txtRol: TextView

    // Presentador de esta pantalla, el id y el tipo del usuario que inició sesión
    val presentador = clsInicioPresenter(this)
    var id = 0
    var tipo = ""
    var nombre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Conectamos los componentes del diseño con las variables
        txtSaludo = findViewById(R.id.txtSaludo)
        cardCatalogo = findViewById(R.id.cardCatalogo)
        cardPrestamos = findViewById(R.id.cardPrestamos)
        cardReservas = findViewById(R.id.cardReservas)
        rvAvisos = findViewById(R.id.rvAvisos)
        navReservas = findViewById(R.id.navReservas)
        navCuenta = findViewById(R.id.navCuenta)
        ivAvisos = findViewById(R.id.ivAvisos)
        navCatalogo = findViewById(R.id.navCatalogo)
        txtRol = findViewById(R.id.txtRol)

        // Recibimos el id que mandó el login con putExtra
        id = intent.getIntExtra("id", 0)
        tipo = intent.getStringExtra("tipo") ?: ""

        // Pedimos al presentador los datos de la pantalla
        presentador.cargarInicio(id)

        // Recibimos el tipo de usuario que mandó el login y se lo pasamos al presentador
        presentador.cargarRol(tipo)

        // Al tocar mis reservas  botón o barra inferior se abre esa pantalla
        cardReservas.setOnClickListener {
            abrirMisReservas()
        }
        navReservas.setOnClickListener {
            abrirMisReservas()
        }
        // Al tocar la barra inferior Cuenta se abre Mi cuenta
        navCuenta.setOnClickListener {
            abrirMiCuenta()
        }
        // Al tocar el botón Préstamos se abre Mis préstamos
        cardPrestamos.setOnClickListener {
            abrirMisPrestamos()
        }
        // La campana abre Avisos
        ivAvisos.setOnClickListener {
            abrirAvisos()
        }
        // El botón Catálogo y la barra inferior abren el catálogo
        cardCatalogo.setOnClickListener {
            abrirCatalogo()
        }
        navCatalogo.setOnClickListener {
            abrirCatalogo()
        }
    }

    // Lo llama el presentador cuando llegan los datos, muestra el saludo y la lista de avisos
    fun mostrarInicio(inicio: dataInicio) {
        nombre = inicio.nombre
        txtSaludo.text = "¡Hola, " + inicio.nombre + "!"
        rvAvisos.layoutManager = LinearLayoutManager(this)
        rvAvisos.adapter = AvisosAdaptador(this, inicio.avisos)
    }

    // Lo llama el presentador: muestra el tipo de usuario
    fun mostrarRol(rol: String) {
        txtRol.text = rol
    }

    // Abre la pantalla Mis reservas mandando el id del usuario
    fun abrirMisReservas() {
        val intent = Intent(this, MisReservasActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("tipo", tipo)
        intent.putExtra("nombre", nombre)
        startActivity(intent)
    }

    // Abre Avisos mandando el id del usuario
    fun abrirAvisos() {
        val intent = Intent(this, Avisos_Notificaciones::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    // Abre el Catálogo mandando id, nombre y tipo
    fun abrirCatalogo() {
        val intent = Intent(this, CatalogoLibrosActivity::class.java)
        intent.putExtra("ID_USUARIO", id)
        intent.putExtra("NOMBRE_LECTOR", nombre)
        intent.putExtra("TIPO_USUARIO", tipo)
        startActivity(intent)
    }

    // Abre la pantalla Mi cuenta mandando el identificador del lector
    fun abrirMiCuenta() {
        val intent = Intent(this, MiCuentaActivity::class.java)
        mandarIdentificador(intent)
        startActivity(intent)
    }

    // Abre la pantalla Mis préstamos mandando el identificador del lector
    fun abrirMisPrestamos() {
        val intent = Intent(this, MisPrestamosActivity::class.java)
        mandarIdentificador(intent)
        startActivity(intent)
    }

    // Alumno manda MATRICULA y trabajador manda NUMERO_CONTROL
    fun mandarIdentificador(intent: Intent) {
        if (tipo == "alumno") {
            intent.putExtra("MATRICULA", id.toString())
        } else {
            intent.putExtra("NUMERO_CONTROL", id.toString())
        }
    }

    // Lo llama el presentador cuando algo falla
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}
