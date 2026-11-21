package com.example.cbtano5.Vista

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.Adaptador.MisReservasAdaptador
import com.example.cbtano5.Modelo.dataReserva
import com.example.cbtano5.Presentador.clsMisReservasPresenter
import com.example.cbtano5.R

class MisReservasActivity : AppCompatActivity() {
    //Definimos los Componentes del diseño
    lateinit var ivVolver: ImageView
    lateinit var txtActivas: TextView
    lateinit var rvReservas: RecyclerView
    lateinit var navInicio: LinearLayout
    lateinit var navCatalogo: LinearLayout
    lateinit var navCuenta: LinearLayout

    //definimos e iniciamos el presentador

    val presentador = clsMisReservasPresenter(this)
    var id = 0
    var tipo = ""
    var nombre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mis_reservas)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //conectamos los componentes del diseño con las variables
        ivVolver = findViewById(R.id.ivVolver)
        txtActivas = findViewById(R.id.txtActivas)
        rvReservas = findViewById(R.id.rvReservas)
        navInicio = findViewById(R.id.navInicio)
        navCatalogo = findViewById(R.id.navCatalogo)
        navCuenta = findViewById(R.id.navCuenta)

        //recibimos el id que mandó la pantalla de inicio
        id = intent.getIntExtra("id", 0)
        tipo = intent.getStringExtra("tipo") ?: ""
        nombre = intent.getStringExtra("nombre") ?: ""

        //le pedimos al presentador las reservas del usuario
        presentador.cargarReservas(id)

        // La flecha y el módulo "Inicio" de la barra inferior regresan a la pantalla anterior
        ivVolver.setOnClickListener {
            finish()
        }
        navInicio.setOnClickListener {
            finish()
        }
        navCatalogo.setOnClickListener {
            abrirCatalogo()
        }
        navCuenta.setOnClickListener {
            abrirMiCuenta()
        }

    }

    // actualiza el contador Activas (n) y llena la lista con el adaptador
    fun mostrarReservas(reservas: List<dataReserva>) {
        txtActivas.text = "Activas (" + reservas.size + ")"
        rvReservas.layoutManager = LinearLayoutManager(this)
        rvReservas.adapter = MisReservasAdaptador(this, reservas)
    }
    //llamamos al presentador al tocar "cancelar"
    //pide confirmar y si el usuario acepta  el presentador cancela la reserva
    fun cancelarReserva(reserva: dataReserva){
        AlertDialog.Builder(this)
            .setTitle("¿Cancelar esta reserva?")
            .setMessage("El ejemplar volverá a estar disponible.")
            .setPositiveButton("Sí, cancelar") { _, _->
                presentador.cancelarReserva(reserva.idReserva, id)
            }
            .setNegativeButton("Volver", null)
            .show()
    }
    //llamamos el adaptador al tocar "comprobante"
    //el presentador arma la direccion del PDF
    fun verComprobante(reserva: dataReserva){
        presentador.verComprobante(reserva.idReserva, id)
    }
    //llamamos el presentador con la dirección del PDF
    // se abre en el navegador para verlo o descargarlo
    // Abre el Catálogo mandando id, nombre y tipo
    fun abrirCatalogo() {
        val intent = Intent(this, CatalogoLibrosActivity::class.java)
        intent.putExtra("ID_USUARIO", id)
        intent.putExtra("NOMBRE_LECTOR", nombre)
        intent.putExtra("TIPO_USUARIO", tipo)
        startActivity(intent)
    }

    // Abre Mi cuenta: alumno manda MATRICULA y trabajador NUMERO_CONTROL
    fun abrirMiCuenta() {
        val intent = Intent(this, MiCuentaActivity::class.java)
        if (tipo == "alumno") {
            intent.putExtra("MATRICULA", id.toString())
        } else {
            intent.putExtra("NUMERO_CONTROL", id.toString())
        }
        startActivity(intent)
    }

    fun abrirComprobante(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    // Muestra un mensaje de éxito (por ejemplo "Reserva cancelada")
    fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    //llamamos el presentador cuando algo falla
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}