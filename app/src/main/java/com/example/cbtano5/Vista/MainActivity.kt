package com.example.cbtano5.Vista

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cbtano5.Presentador.clsLoginPresenter
import com.example.cbtano5.R

class MainActivity : AppCompatActivity() {
    //definimos los componentes
    lateinit var txtNumeroControl: EditText
    lateinit var txtContrasenia: EditText
    lateinit var btnIngresar: Button
    lateinit var txtOlvidaste: TextView
    lateinit var ivVerContrasenia: ImageView
    var contraseniaVisible = false
    //creamos el presentador
    val presentador = clsLoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //inicializamos las variables con los componentes
        txtNumeroControl = findViewById(R.id.txtNumeroControl)
        txtContrasenia = findViewById(R.id.txtContrasenia)
        btnIngresar = findViewById(R.id.btnIngresar)
        txtOlvidaste = findViewById(R.id.txtOlvidaste)
        ivVerContrasenia = findViewById(R.id.ivVerContrasenia)

        // Subraya el texto "¿Olvidaste tu contraseña?"
        txtOlvidaste.paintFlags = txtOlvidaste.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        //inicializamos el boton con listener
        btnIngresar.setOnClickListener { ejecutarIniciarSesion() }
        ivVerContrasenia.setOnClickListener { cambiarVisibilidadContrasenia() }
    }
    fun ejecutarIniciarSesion() {
        presentador.iniciarSesion(
            txtNumeroControl.text.toString().trim(),
            txtContrasenia.text.toString()
        )
    }
    // Muestra u oculta la contraseña al tocar el ojo
    fun cambiarVisibilidadContrasenia() {
        if (contraseniaVisible) {
            txtContrasenia.transformationMethod = PasswordTransformationMethod.getInstance()
            ivVerContrasenia.setImageResource(R.drawable.ic_eye_off)
            contraseniaVisible = false
        } else {
            txtContrasenia.transformationMethod = HideReturnsTransformationMethod.getInstance()
            ivVerContrasenia.setImageResource(R.drawable.ic_eye)
            contraseniaVisible = true
        }
        // deja el cursor al final del texto
        txtContrasenia.setSelection(txtContrasenia.text.length)
    }
    // abre la pantalla de Inicio y le manda el id del usuario
    fun abrirInicio(id: Int, tipo: String) {
        val intent = Intent(this, InicioActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("tipo", tipo)   //alumno o trabajador
        startActivity(intent)
        finish()   // cierra el login para que no se pueda regresar con el botón atrás
    }

    //lo llama el presentador cuando algo falla, muestra el mensaje en pantalla
    fun mostrarError(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}
