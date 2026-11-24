package com.example.cbtano5.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cbtano5.Presentador.clsRecuperarContraseniaPresenter
import com.example.cbtano5.R

class RecuperarContraseniaActivity : AppCompatActivity() {
    // definimos los componentes
    lateinit var ivVolver: ImageView
    lateinit var txtNumeroControl: EditText
    lateinit var btnContinuar: Button
    // creamos el presentador
    val presentador = clsRecuperarContraseniaPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperar_contrasenia)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // conectamos los componentes del diseño con las variables
        ivVolver = findViewById(R.id.ivVolver)
        txtNumeroControl = findViewById(R.id.txtNumeroControl)
        btnContinuar = findViewById(R.id.btnContinuar)

        // la flecha regresa al login y el botón busca la pregunta
        ivVolver.setOnClickListener { finish() }
        btnContinuar.setOnClickListener { ejecutarBuscarPregunta() }
    }
    // manda al presentador el número de control escrito
    fun ejecutarBuscarPregunta() {
        presentador.buscarPregunta(txtNumeroControl.text.toString().trim())
    }

    // lo llama el presentador abre la pantalla de la pregunta mandando los datos
    fun abrirPregunta(numeroControl: String, pregunta: String) {
        val intent = Intent(this, PreguntaSeguridadActivity::class.java)
        intent.putExtra("numero_control", numeroControl)
        intent.putExtra("pregunta", pregunta)
        startActivity(intent)
    }

    // lo llama el presentador cuando algo falla
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}