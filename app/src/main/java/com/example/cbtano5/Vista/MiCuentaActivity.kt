package com.example.cbtano5.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cbtano5.R
import com.example.cbtano5.Modelo.MiCuentaResponse
import com.example.cbtano5.Presentador.clsMiCuentaPresentador
import android.graphics.Color
import androidx.core.view.WindowCompat

class MiCuentaActivity : AppCompatActivity() {

    // Presentador
    private lateinit var presenter: clsMiCuentaPresentador

    // Componentes del XML
    private lateinit var txtNombrePerfil: TextView
    private lateinit var txtTipoUsuario: TextView
    private lateinit var txtNombre: TextView
    private lateinit var txtCarrera: TextView
    private lateinit var btnRegresar: TextView

    // Opciones del menú
    private lateinit var opMisPrestamos: LinearLayout
    private lateinit var opMisAdeudos: LinearLayout
    private lateinit var opCambiarContrasena: LinearLayout
    private lateinit var btnCerrarSesion: Button

    // Barra de navegación inferior
    private var navInicio: LinearLayout? = null
    private var navCatalogo: LinearLayout? = null
    private var navReservas: LinearLayout? = null
    private var navCuenta: LinearLayout? = null

    // Identificadores y datos del lector
    private var matricula: String = ""
    private var numeroControl: String = ""
    private var idLector: Int = 0
    private var tipoUsuarioStr: String = ""

    // Datos originales del usuario
    private var idUsuarioOriginal: Int = 0
    private var nombreUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_cuenta)


        // Relacionar los componentes con el XML
        txtNombrePerfil = findViewById(R.id.txtNombrePerfil)
        txtTipoUsuario = findViewById(R.id.txtTipoUsuario)
        txtNombre = findViewById(R.id.txtNombre)
        txtCarrera = findViewById(R.id.txtCarrera)
        btnRegresar = findViewById(R.id.btnRegresar)

        // Relacionar las opciones del menú
        opMisPrestamos = findViewById(R.id.opMisPrestamos)
        opMisAdeudos = findViewById(R.id.opMisAdeudos)
        opCambiarContrasena = findViewById(R.id.opCambiarContrasena)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        // Relacionar la barra de navegación inferior
        navInicio = findViewById(R.id.nav_inicio)
        navCatalogo = findViewById(R.id.nav_catalogo)
        navReservas = findViewById(R.id.nav_reservas)
        navCuenta = findViewById(R.id.nav_cuenta)

        // Inicializar el presentador
        presenter = clsMiCuentaPresentador(this)

        // Recibir identificadores enviados por otra pantalla
        matricula = intent.getStringExtra("MATRICULA") ?: ""
        numeroControl = intent.getStringExtra("NUMERO_CONTROL") ?: ""

        idLector = intent.getIntExtra("id", 0)
            .takeIf { it != 0 }
            ?: intent.getIntExtra("ID_USUARIO", 0)

        // Conservar el identificador original del usuario
        idUsuarioOriginal = matricula.toIntOrNull()
            ?: numeroControl.toIntOrNull()
                    ?: idLector

        // Identificar el tipo de usuario
        tipoUsuarioStr = when {
            matricula.isNotBlank() -> "alumno"
            numeroControl.isNotBlank() -> "trabajador"
            else -> (
                    intent.getStringExtra("tipo")
                        ?: intent.getStringExtra("TIPO_USUARIO")
                        ?: ""
                    ).trim().lowercase()
        }

        // Recuperar el identificador si viene desde otra vista
        if (matricula.isBlank() && numeroControl.isBlank()
            && idUsuarioOriginal > 0
        ) {
            when (tipoUsuarioStr) {
                "alumno" -> {
                    matricula = idUsuarioOriginal.toString()
                }

                "trabajador" -> {
                    numeroControl = idUsuarioOriginal.toString()
                }
            }
        }

        // Recibir el nombre si otra pantalla lo envía
        nombreUsuario = intent.getStringExtra("nombre")
            ?: intent.getStringExtra("NOMBRE_LECTOR")
                    ?: ""

        // Información inicial
        txtNombrePerfil.text = "Cargando cuenta..."
        txtTipoUsuario.text = ""
        txtNombre.text = "No disponible"
        txtCarrera.text = "No disponible"

        // Botón regresar
        btnRegresar.setOnClickListener {
            finish()
        }

        // 1. Abrir Mis préstamos
        opMisPrestamos.setOnClickListener {

            val intentPrestamos = Intent(this, MisPrestamosActivity::class.java)

            intentPrestamos.putExtra("MATRICULA", matricula)
            intentPrestamos.putExtra("NUMERO_CONTROL", numeroControl)

            startActivity(intentPrestamos)
        }

        // 2. Abrir Mis adeudos
        opMisAdeudos.setOnClickListener {

            val idFinal = idLector.takeIf { it != 0 }
                ?: idUsuarioOriginal

            val intentAdeudos = Intent(this, Mis_Adeudos::class.java)

            intentAdeudos.putExtra(Mis_Adeudos.EXTRA_ID_LECTOR, idFinal)

            startActivity(intentAdeudos)
        }

        // 3. Abrir Cambiar contraseña
        opCambiarContrasena.setOnClickListener {

            if (idUsuarioOriginal > 0 && tipoUsuarioStr.isNotBlank()) {

                val intentCambio = Intent(this, CambiarContraseniaActivity::class.java)

                intentCambio.putExtra("ID_USUARIO", idUsuarioOriginal)
                intentCambio.putExtra("TIPO_USUARIO", tipoUsuarioStr)

                startActivity(intentCambio)

            } else {
                mostrarErrorIdentificador()
            }
        }
        // 4. Cerrar sesión
        btnCerrarSesion.setOnClickListener {

            val intentLogin = Intent(this, MainActivity::class.java)

            intentLogin.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intentLogin)
            finish()
        }

        // Barra de navegación inferior

        // Abrir Inicio
        navInicio?.setOnClickListener {
            abrirInicio()
        }

        // Abrir Catálogo
        navCatalogo?.setOnClickListener {
            abrirCatalogo()
        }

        // Abrir Mis reservas
        navReservas?.setOnClickListener {
            abrirMisReservas()
        }

        // Permanecer en Mi cuenta
        navCuenta?.setOnClickListener {
            // Ya estamos en Mi cuenta
        }

        // Consultar la cuenta mediante MVP
        presenter.cargarMiCuenta(matricula, numeroControl)
    }

    // Abrir la pantalla Inicio
    fun abrirInicio() {

        if (idUsuarioOriginal > 0) {

            val intentInicio = Intent(this, InicioActivity::class.java)

            intentInicio.putExtra("id", idUsuarioOriginal)
            intentInicio.putExtra("tipo", tipoUsuarioStr)
            intentInicio.putExtra("nombre", nombreUsuario)

            startActivity(intentInicio)
            finish()

        } else {
            mostrarErrorIdentificador()
        }
    }

    // Abrir la pantalla Catálogo
    fun abrirCatalogo() {

        if (idUsuarioOriginal > 0) {

            val intentCatalogo = Intent(this, CatalogoLibrosActivity::class.java)

            intentCatalogo.putExtra("ID_USUARIO", idUsuarioOriginal)

            intentCatalogo.putExtra("NOMBRE_LECTOR", nombreUsuario)

            intentCatalogo.putExtra("TIPO_USUARIO", tipoUsuarioStr)

            startActivity(intentCatalogo)
            finish()

        } else {
            mostrarErrorIdentificador()
        }
    }

    // Abrir la pantalla Mis reservas
    fun abrirMisReservas() {

        if (idUsuarioOriginal > 0) {

            val intentReservas = Intent(this, MisReservasActivity::class.java)

            // Enviar los datos que espera MisReservasActivity
            intentReservas.putExtra("id", idUsuarioOriginal)
            intentReservas.putExtra("tipo", tipoUsuarioStr)
            intentReservas.putExtra("nombre", nombreUsuario)

            startActivity(intentReservas)

        } else {
            mostrarErrorIdentificador()
        }
    }

    // Mostrar información obtenida de MySQL
    fun mostrarMiCuenta(cuenta: MiCuentaResponse) {

        val datos = cuenta.data ?: return
        val lector = datos.cuenta

        // Obtener el identificador interno de la cuenta
        // Sin modificar el identificador original del usuario
        idLector = lector.id_cuenta.takeIf { it != 0 }
            ?: idLector.takeIf { it != 0 }
                    ?: idUsuarioOriginal

        // Conservar el tipo original del usuario
        if (tipoUsuarioStr.isBlank()) {
            tipoUsuarioStr = lector.tipo_lector.trim().lowercase()
        }

        // Obtener el nombre completo
        val nombreCompleto = lector.nombre_completo
            ?.takeIf { it.isNotBlank() }
            ?: "Nombre no disponible"

        // Guardar el nombre para enviarlo a otras pantallas
        nombreUsuario = nombreCompleto

        // Obtener el nombre de la carrera
        val carreraLector = (lector.carrera ?: lector.nombre_carrera)
            ?.takeIf { it.isNotBlank() }
            ?: "No disponible"

        // Mostrar información en la vista
        txtNombrePerfil.text = nombreCompleto
        txtNombre.text = nombreCompleto
        txtTipoUsuario.text = lector.tipo_lector
        txtCarrera.text = carreraLector
    }

    // Mostrar error cuando no existe un identificador válido
    private fun mostrarErrorIdentificador() {

        Toast.makeText(this, "No se encontró el identificador del usuario", Toast.LENGTH_SHORT).show()
    }

    // Mostrar los mensajes de error del presentador
    fun mostrarError(mensaje: String) {

        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()

        txtNombrePerfil.text = "No se pudo cargar la cuenta"
    }
}