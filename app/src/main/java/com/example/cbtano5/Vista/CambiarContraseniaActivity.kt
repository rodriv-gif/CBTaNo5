package com.example.cbtano5.Vista

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cbtano5.Presentador.clsCambiarContraseniaPresentador
import com.example.cbtano5.R

class CambiarContraseniaActivity : AppCompatActivity() {

    private lateinit var presentador: clsCambiarContraseniaPresentador
    private lateinit var btnVolverCambioContrasenia: ImageButton
    private lateinit var txtContraseniaActual: EditText
    private lateinit var txtNuevaContrasenia: EditText
    private lateinit var txtConfirmarContrasenia: EditText
    private lateinit var btnActualizarContrasenia: Button
    private var tipoUsuario: String = ""
    private var identificador: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cambio_contrasenia)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lytRaizCambioContrasenia)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnVolverCambioContrasenia = findViewById(R.id.btnVolverCambioContrasenia)
        txtContraseniaActual = findViewById(R.id.txtContraseniaActual)
        txtNuevaContrasenia = findViewById(R.id.txtNuevaContrasenia)
        txtConfirmarContrasenia = findViewById(R.id.txtConfirmarContrasenia)
        btnActualizarContrasenia = findViewById(R.id.btnActualizarContrasenia)

        presentador = clsCambiarContraseniaPresentador(this)

        identificador = intent.getIntExtra("ID_USUARIO", 0)
        tipoUsuario = intent.getStringExtra("TIPO_USUARIO") ?: ""

        btnVolverCambioContrasenia.setOnClickListener { finish() }
        btnActualizarContrasenia.setOnClickListener(this::ejecutarCambioContrasenia)
    }

    fun ejecutarCambioContrasenia(v: View) {
        val actual = txtContraseniaActual.text.toString().trim()
        val nueva = txtNuevaContrasenia.text.toString().trim()
        val confirmar = txtConfirmarContrasenia.text.toString().trim()
        presentador.cambiarContrasenia(tipoUsuario, identificador, actual, nueva, confirmar)
    }

    fun mostrarExito(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}