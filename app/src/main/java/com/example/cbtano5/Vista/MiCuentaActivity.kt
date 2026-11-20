package com.example.cbtano5.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cbtano5.R
import com.example.cbtano5.Modelo.MiCuentaResponse
import com.example.cbtano5.Presentador.clsMiCuentaPresentador
//import kotlinx.serialization.json.content

class MiCuentaActivity : AppCompatActivity() {

    // Presentador
    private lateinit var presenter: clsMiCuentaPresentador

    // Componentes del XML
    private lateinit var txtNombrePerfil: TextView
    private lateinit var txtTipoUsuario: TextView
    private lateinit var txtNombre: TextView
    private lateinit var txtCarrera: TextView
    private lateinit var btnRegresar: TextView

    // Opción Mis préstamos
    private lateinit var opMisPrestamos: LinearLayout

    // Identificadores del lector
    private var matricula: String = ""
    private var numeroControl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_cuenta)

        // Relacionar los componentes con el XML
        txtNombrePerfil = findViewById(R.id.txtNombrePerfil)
        txtTipoUsuario = findViewById(R.id.txtTipoUsuario)
        txtNombre = findViewById(R.id.txtNombre)
        txtCarrera = findViewById(R.id.txtCarrera)
        btnRegresar = findViewById(R.id.btnRegresar)

        opMisPrestamos = findViewById(R.id.opMisPrestamos)

        // Inicializar el presentador
        presenter = clsMiCuentaPresentador(this)

        // Recibir identificadores enviados por otra pantalla
        matricula = intent.getStringExtra("MATRICULA") ?: ""
        numeroControl = intent.getStringExtra("NUMERO_CONTROL") ?: ""

        // Información inicial
        txtNombrePerfil.text = "Cargando cuenta..."
        txtTipoUsuario.text = ""
        txtNombre.text = "No disponible"
        txtCarrera.text = "No disponible"

        // Botón regresar
        btnRegresar.setOnClickListener {
            finish()
        }

        // Abrir Mis préstamos
        opMisPrestamos.setOnClickListener {

            val intentPrestamos = Intent(
                this,
                MisPrestamosActivity::class.java
            )

            // Enviar el identificador del lector
            intentPrestamos.putExtra("MATRICULA", matricula)
            intentPrestamos.putExtra("NUMERO_CONTROL", numeroControl)

            startActivity(intentPrestamos)
        }

        // Consultar la cuenta mediante MVP
        presenter.cargarMiCuenta(matricula, numeroControl)
    }

    // Mostrar información obtenida de MySQL
    fun mostrarMiCuenta(cuenta: MiCuentaResponse) {

        val datos = cuenta.data ?: return
        val lector = datos.cuenta

        // Mostrar tipo de usuario
        txtTipoUsuario.text = lector.tipo_lector

        // Obtener matrícula o número de control
        val identificador = lector.matricula?.content
            ?: lector.numero_control
            ?: "No disponible"

        // Mostrar identificador en el perfil
        txtNombrePerfil.text = identificador

        // La API actual no devuelve nombre ni carrera
        txtNombre.text = "No disponible"
        txtCarrera.text = "No disponible"

        val nombreCompleto = lector.nombre_completo
            ?.takeIf { it.isNotBlank() }
            ?: "Nombre no disponible"

        txtNombrePerfil.text = nombreCompleto
        txtNombre.text = nombreCompleto
        txtTipoUsuario.text = lector.tipo_lector
    }

    // Mostrar los mensajes de error
    fun mostrarError(mensaje: String) {

        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()

        txtNombrePerfil.text = "No se pudo cargar la cuenta"
    }
}
