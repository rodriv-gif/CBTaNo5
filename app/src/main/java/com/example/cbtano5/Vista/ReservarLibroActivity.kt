package com.example.cbtano5.Vista

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cbtano5.Presentador.clsReservarPresentador
import com.example.cbtano5.R
import com.example.cbtano5.Modelo.dataReserva
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReservarLibroActivity : AppCompatActivity() {
    private lateinit var presentador: clsReservarPresentador
    private lateinit var btnVolverReservar: ImageButton
    private lateinit var btnConfirmarReserva: Button

    //Datos a mandar despues de seleccionar en la vista catalogo
    private lateinit var imgPortadaLibro: ImageView
    private lateinit var txtTituloLibro: TextView
    private lateinit var txtAutorLibro: TextView
    private lateinit var txtCategoriaLibro: TextView
    private lateinit var txtLector: EditText
    private lateinit var txtFechaRecoger: EditText
    private lateinit var txtTipoUsuario: EditText
    private var idLibro: Int = 0
    private var identificador: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reservar_libro)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lytRaizReservar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            findViewById<View>(R.id.lytEncabezadoReservar).setPadding(0, systemBars.top, 0, 0)
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }
        btnVolverReservar = findViewById(R.id.btnVolverReservar)
        btnConfirmarReserva = findViewById(R.id.btnConfirmarReserva)
        imgPortadaLibro = findViewById(R.id.imgPortadaLibro)
        txtTituloLibro = findViewById(R.id.txtTituloLibro)
        txtAutorLibro = findViewById(R.id.txtAutorLibro)
        txtCategoriaLibro = findViewById(R.id.txtCategoriaLibro)
        txtLector = findViewById(R.id.txtLector)
        txtFechaRecoger = findViewById(R.id.txtFechaRecoger)
        txtTipoUsuario = findViewById(R.id.txtTipoUsuario)
        presentador = clsReservarPresentador(this)

        idLibro = intent.getIntExtra("ID_LIBRO", 0)
        txtTituloLibro.text = intent.getStringExtra("TITULO_LIBRO") ?: ""
        txtAutorLibro.text = intent.getStringExtra("AUTOR_LIBRO") ?: ""
        txtCategoriaLibro.text = intent.getStringExtra("CATEGORIA_LIBRO") ?: ""
        identificador = intent.getIntExtra("ID_USUARIO", 0)
        txtLector.setText(intent.getStringExtra("NOMBRE_LECTOR") ?: "")
        txtTipoUsuario.setText(intent.getStringExtra("TIPO_USUARIO") ?: "")
        
        val portada = intent.getStringExtra("PORTADA_LIBRO")?.trim() ?: ""
        val baseUrl = "https://biblioteccbtan5.4cuthh.com/bibliocbta5/"
        val rutaPortadas = "upload/libros/principales/"
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

        txtFechaRecoger.setText(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
        txtFechaRecoger.isEnabled = false

        btnVolverReservar.setOnClickListener { finish() }
        btnConfirmarReserva.setOnClickListener(this::ejecutarReserva)

    }//Fin del onCreat
    fun ejecutarReserva(v: View) {
        val tipoUsuario = txtTipoUsuario.text.toString().trim()
        val fechaRecoger = txtFechaRecoger.text.toString().trim()
        presentador.crearReserva(tipoUsuario, identificador, idLibro, fechaRecoger)
    }
    fun reservaExitosa(datos: Any) {
        val reservaData = datos as? dataReserva
        Toast.makeText(this, "Reserva realizada con éxito", Toast.LENGTH_SHORT).show()
        
        val intent = android.content.Intent(this, Reserva_Realizada::class.java)
        intent.putExtra(Reserva_Realizada.EXTRA_ID_LECTOR, identificador)
        if (reservaData != null) {
            intent.putExtra(Reserva_Realizada.EXTRA_ID_RESERVA, reservaData.idReserva)
        }
        startActivity(intent)
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

}