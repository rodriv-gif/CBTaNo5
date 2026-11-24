package com.example.cbtano5.Vista

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.R
import com.example.cbtano5.Adaptador.PrestamosAdaptador
import com.example.cbtano5.Modelo.MisPrestamosResponse
import com.example.cbtano5.Modelo.dataLibro
import com.example.cbtano5.Presentador.clsMisPrestamosPresentador

class MisPrestamosActivity : AppCompatActivity() {

    private lateinit var presenter: clsMisPrestamosPresentador

    private lateinit var txtPrestamosActivos: TextView
    private lateinit var txtSinPrestamos: TextView
    private lateinit var btnRegresar: TextView
    private lateinit var rvPrestamos: RecyclerView

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

    fun mostrarPrestamos(prestamos: MisPrestamosResponse, librosPorId: Map<Int, dataLibro>) {

        val datos = prestamos.data ?: return

        // Préstamos sin devolver
        val listaActivos = datos.prestamos.filter {

            (it.idEstadoPrestamo == 1 ||
                    it.idEstadoPrestamo == 3) &&
                    it.fechaDevolucionReal.isNullOrBlank()
        }

        // Actualizar RecyclerView
        adaptador.actualizarLista(listaActivos, librosPorId)

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
