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

    // Identificadores y datos del lector
    private var matricula: String = ""
    private var numeroControl: String = ""
    private var idLector: Int = 0
    private var tipoUsuarioStr: String = ""

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
        opMisAdeudos = findViewById(R.id.opMisAdeudos)
        opCambiarContrasena = findViewById(R.id.opCambiarContrasena)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        navInicio = findViewById(R.id.nav_inicio)
        navCatalogo = findViewById(R.id.nav_catalogo)
        navReservas = findViewById(R.id.nav_reservas)

        // Inicializar el presentador
        presenter = clsMiCuentaPresentador(this)

        // Recibir identificadores enviados por otra pantalla
        matricula = intent.getStringExtra("MATRICULA") ?: ""
        numeroControl = intent.getStringExtra("NUMERO_CONTROL") ?: ""
        idLector = intent.getIntExtra("id", 0).takeIf { it != 0 }
            ?: intent.getIntExtra("ID_USUARIO", 0)

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
                ?: matricula.toIntOrNull()
                ?: numeroControl.toIntOrNull()
                ?: 0

            val intentAdeudos = Intent(this, Mis_Adeudos::class.java)
            intentAdeudos.putExtra(Mis_Adeudos.EXTRA_ID_LECTOR, idFinal)
            startActivity(intentAdeudos)
        }

        // 3. Abrir Cambiar contraseña
        opCambiarContrasena.setOnClickListener {
            val idFinal = idLector.takeIf { it != 0 }
                ?: matricula.toIntOrNull()
                ?: numeroControl.toIntOrNull()
                ?: 0

            val intentCambio = Intent(this, CambiarContraseniaActivity::class.java)
            intentCambio.putExtra("ID_USUARIO", idFinal)
            intentCambio.putExtra("TIPO_USUARIO", tipoUsuarioStr)
            startActivity(intentCambio)
        }

        // 4. Cerrar sesión
        btnCerrarSesion.setOnClickListener {
            val intentLogin = Intent(this, MainActivity::class.java)
            intentLogin.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentLogin)
            finish()
        }

        // Navegación Inferior
        navInicio?.setOnClickListener {
            finish()
        }

        navCatalogo?.setOnClickListener {
            val idFinal = idLector.takeIf { it != 0 }
                ?: matricula.toIntOrNull()
                ?: numeroControl.toIntOrNull()
                ?: 0

            val intentCat = Intent(this, CatalogoLibrosActivity::class.java)
            intentCat.putExtra("ID_USUARIO", idFinal)
            intentCat.putExtra("NOMBRE_LECTOR", txtNombrePerfil.text.toString())
            intentCat.putExtra("TIPO_USUARIO", tipoUsuarioStr)
            startActivity(intentCat)
        }

        navReservas?.setOnClickListener {
            val idFinal = idLector.takeIf { it != 0 }
                ?: matricula.toIntOrNull()
                ?: numeroControl.toIntOrNull()
                ?: 0

            val intentRes = Intent(this, MisReservasActivity::class.java)
            intentRes.putExtra("id", idFinal)
            intentRes.putExtra("tipo", tipoUsuarioStr)
            intentRes.putExtra("nombre", txtNombrePerfil.text.toString())
            startActivity(intentRes)
        }

        // Consultar la cuenta mediante MVP
        presenter.cargarMiCuenta(matricula, numeroControl)
    }

    // Mostrar información obtenida de MySQL
    fun mostrarMiCuenta(cuenta: MiCuentaResponse) {
        val datos = cuenta.data ?: return
        val lector = datos.cuenta

        idLector = lector.id_cuenta.takeIf { it != 0 }
            ?: idLector.takeIf { it != 0 }
            ?: matricula.toIntOrNull()
            ?: numeroControl.toIntOrNull()
            ?: 0

        tipoUsuarioStr = lector.tipo_lector

        // Mostrar tipo de usuario
        txtTipoUsuario.text = lector.tipo_lector

        // Obtener matrícula o número de control
        val identificador = lector.matricula?.content
            ?: lector.numero_control
            ?: "No disponible"

        // Mostrar identificador en el perfil
        txtNombrePerfil.text = identificador

        val nombreCompleto = lector.nombre_completo
            ?.takeIf { it.isNotBlank() }
            ?: "Nombre no disponible"

        val carreraLector = (lector.carrera ?: lector.nombre_carrera)
            ?.takeIf { it.isNotBlank() }
            ?: "No disponible"

        txtNombrePerfil.text = nombreCompleto
        txtNombre.text = nombreCompleto
        txtTipoUsuario.text = lector.tipo_lector
        txtCarrera.text = carreraLector
    }

    // Mostrar los mensajes de error
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        txtNombrePerfil.text = "No se pudo cargar la cuenta"
    }
}
