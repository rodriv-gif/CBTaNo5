package com.example.cbtano5.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cbtano5.Presentador.clsPreguntaSeguridadPresenter
import com.example.cbtano5.R

class PreguntaSeguridadActivity : AppCompatActivity() {
    // definimos los componentes
    lateinit var ivVolver: ImageView
    lateinit var txtPregunta: TextView
    lateinit var txtRespuesta: EditText
    lateinit var txtNuevaContrasenia: EditText
    lateinit var txtConfirmarContrasenia: EditText
    lateinit var btnCambiar: Button
    // creamos el presentador y la variable del número de control
    val presentador = clsPreguntaSeguridadPresenter(this)
    var numeroControl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pregunta_seguridad)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // conectamos los componentes del diseño con las variables
        ivVolver = findViewById(R.id.ivVolver)
        txtPregunta = findViewById(R.id.txtPregunta)
        txtRespuesta = findViewById(R.id.txtRespuesta)
        txtNuevaContrasenia = findViewById(R.id.txtNuevaContrasenia)
        txtConfirmarContrasenia = findViewById(R.id.txtConfirmarContrasenia)
        btnCambiar = findViewById(R.id.btnCambiar)

        // recibimos los datos que mandó la pantalla anterior
        numeroControl = intent.getStringExtra("numero_control") ?: ""
        txtPregunta.text = intent.getStringExtra("pregunta") ?: ""

        // la flecha regresa a la pantalla anterior y el botón cambia la contraseña
        ivVolver.setOnClickListener { finish() }
        btnCambiar.setOnClickListener { ejecutarCambiarContrasenia() }
    }
    // manda al presentador lo que escribió el lector
    fun ejecutarCambiarContrasenia() {
        presentador.cambiarContrasenia(
            numeroControl,
            txtRespuesta.text.toString().trim(),
            txtNuevaContrasenia.text.toString(),
            txtConfirmarContrasenia.text.toString()
        )
    }

    // lo llama el presentador muestra el mensaje y regresa al login
    fun mostrarExito(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP   // cierra las pantallas que quedaron encima del login
        startActivity(intent)
        finish()
    }

    // lo llama el presentador cuando algo falla
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}