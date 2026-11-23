package com.example.cbtano5.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.R
import com.example.cbtano5.Adaptador.PrestamosAdaptador
import com.example.cbtano5.Modelo.MisPrestamosResponse
import com.example.cbtano5.Presentador.clsMisPrestamosPresentador

class MisPrestamosActivity : AppCompatActivity() {

    private lateinit var presenter: clsMisPrestamosPresentador

    private lateinit var txtPrestamosActivos: TextView
    private lateinit var txtSinPrestamos: TextView
    private lateinit var btnRegresar: TextView
    private lateinit var rvPrestamos: RecyclerView

    private lateinit var navInicio: LinearLayout
    private lateinit var navCatalogo: LinearLayout
    private lateinit var navReservas: LinearLayout
    private lateinit var navCuenta: LinearLayout

    private lateinit var adaptador: PrestamosAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_prestamos)

        // Relaciona componentes del X
        txtPrestamosActivos = findViewById(R.id.txtPrestamosActivos)
        txtSinPrestamos = findViewById(R.id.txtSinPrestamos)
        btnRegresar = findViewById(R.id.btnRegresar)
        rvPrestamos = findViewById(R.id.rvPrestamos)

        // Configurar lista
        rvPrestamos.layoutManager = LinearLayoutManager(this)

        adaptador = PrestamosAdaptador(emptyList())

        rvPrestamos.adapter = adaptador

        // Inicializar presentador
        presenter = clsMisPrestamosPresentador(this)

        // Identificadores recibidos
        val matricula = intent.getStringExtra("MATRICULA") ?: ""
        val numeroControl = intent.getStringExtra("NUMERO_CONTROL") ?: ""

        navInicio = findViewById(R.id.nav_inicio)
        navCatalogo = findViewById(R.id.nav_catalogo)
        navReservas = findViewById(R.id.nav_reservas)
        navCuenta = findViewById(R.id.nav_cuenta)

        val idUsuario = intent.getIntExtra("ID_USUARIO", 0).takeIf { it != 0 }
            ?: intent.getIntExtra("id", 0).takeIf { it != 0 }
            ?: matricula.toIntOrNull()
            ?: numeroControl.toIntOrNull()
            ?: 0
        val tipoUsuario = intent.getStringExtra("TIPO_USUARIO") ?: intent.getStringExtra("tipo") ?: if (matricula.isNotEmpty()) "alumno" else "trabajador"
        val nombreLector = intent.getStringExtra("NOMBRE_LECTOR") ?: intent.getStringExtra("nombre") ?: ""

        navInicio.setOnClickListener {
            val intentNav = Intent(this, InicioActivity::class.java)
            intentNav.putExtra("id", idUsuario)
            intentNav.putExtra("tipo", tipoUsuario)
            startActivity(intentNav)
            finish()
        }

        navCatalogo.setOnClickListener {
            val intentNav = Intent(this, CatalogoLibrosActivity::class.java)
            intentNav.putExtra("ID_USUARIO", idUsuario)
            intentNav.putExtra("NOMBRE_LECTOR", nombreLector)
            intentNav.putExtra("TIPO_USUARIO", tipoUsuario)
            startActivity(intentNav)
            finish()
        }

        navReservas.setOnClickListener {
            val intentNav = Intent(this, MisReservasActivity::class.java)
            intentNav.putExtra("id", idUsuario)
            intentNav.putExtra("tipo", tipoUsuario)
            intentNav.putExtra("nombre", nombreLector)
            startActivity(intentNav)
            finish()
        }

        navCuenta.setOnClickListener {
            val intentNav = Intent(this, MiCuentaActivity::class.java)
            if (matricula.isNotEmpty()) {
                intentNav.putExtra("MATRICULA", matricula)
            } else if (numeroControl.isNotEmpty()) {
                intentNav.putExtra("NUMERO_CONTROL", numeroControl)
            } else if (idUsuario != 0) {
                if (tipoUsuario.lowercase() == "alumno") {
                    intentNav.putExtra("MATRICULA", idUsuario.toString())
                } else {
                    intentNav.putExtra("NUMERO_CONTROL", idUsuario.toString())
                }
            }
            intentNav.putExtra("id", idUsuario)
            startActivity(intentNav)
            finish()
        }

        // Botón regresar
        btnRegresar.setOnClickListener {
            finish()
        }

        // Estado inicial
        txtPrestamosActivos.text = "Cargando préstamos..."
        txtSinPrestamos.visibility = View.GONE
        rvPrestamos.visibility = View.VISIBLE

        // Consultar MySQL
        presenter.cargarPrestamos(matricula, numeroControl)
    }

    fun mostrarPrestamos(prestamos: MisPrestamosResponse) {

        val datos = prestamos.data ?: return

        // Préstamos sin devolver
        val listaActivos = datos.prestamos.filter {

            (it.idEstadoPrestamo == 1 ||
                    it.idEstadoPrestamo == 3) &&
                    it.fechaDevolucionReal.isNullOrBlank()
        }

        // Actualizar RecyclerView
        adaptador.actualizarLista(listaActivos)

        // Cantidad de préstamos activos
        txtPrestamosActivos.text = "Activos (${listaActivos.size})"

        // Comprobar si existen préstamos
        if (listaActivos.isEmpty()) {

            rvPrestamos.visibility = View.GONE

            txtSinPrestamos.text = "No tienes préstamos activos"

            txtSinPrestamos.visibility = View.VISIBLE

        } else {

            rvPrestamos.visibility = View.VISIBLE

            txtSinPrestamos.visibility = View.GONE
        }
    }

    fun mostrarError(mensaje: String) {

        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()

        txtPrestamosActivos.text = "Mis préstamos"

        txtSinPrestamos.text =
            "No se pudieron cargar los préstamos"

        txtSinPrestamos.visibility = View.VISIBLE

        rvPrestamos.visibility = View.GONE
    }
}
