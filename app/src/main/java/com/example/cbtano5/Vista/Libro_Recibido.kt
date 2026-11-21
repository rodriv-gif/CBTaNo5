package com.example.cbtano5.Vista

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cbtano5.Modelo.PrestamoConfirmado
import com.example.cbtano5.Presentador.clsLibroRecibidoPresentador
import com.example.cbtano5.R

class Libro_Recibido : AppCompatActivity() {

    companion object {

        // Opción 1 (la buena): mandar el id del lector y de la reserva.
        // Al abrirse, la pantalla confirma el préstamo en confirmar_recibido.php.
        //   val intent = Intent(this, Libro_Recibido::class.java)
        //   intent.putExtra(Libro_Recibido.EXTRA_ID_LECTOR, id)
        //   intent.putExtra(Libro_Recibido.EXTRA_ID_RESERVA, idReserva)
        //   startActivity(intent)
        const val EXTRA_ID_LECTOR = "id"
        const val EXTRA_ID_RESERVA = "id_reserva"

        // Opción 2: mandar directamente el mensaje ya listo (no llama a la API).
        //   intent.putExtra(Libro_Recibido.EXTRA_MENSAJE, "Préstamo confirmado. Devuélvelo el 25/11/2026.")
        const val EXTRA_MENSAJE = "mensaje"
    }

    private lateinit var textoMensaje: TextView

    private lateinit var presentador: clsLibroRecibidoPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro_recibido)

        ajustarBarrasDelSistema(
            findViewById(R.id.raiz),
            findViewById(R.id.encabezado)
        )

        textoMensaje = findViewById(R.id.texto_mensaje)

        findViewById<Button>(R.id.btn_aceptar).setOnClickListener {
            finish()
        }

        presentador = clsLibroRecibidoPresentador(this)

        val mensajeRecibido = intent.getStringExtra(EXTRA_MENSAJE)
        val idLector = intent.getIntExtra(EXTRA_ID_LECTOR, 0)
        val idReserva = intent.getIntExtra(EXTRA_ID_RESERVA, 0)

        if (mensajeRecibido != null) {

            textoMensaje.text = mensajeRecibido

        } else if (idLector > 0 && idReserva > 0) {

            // Mientras responde la API se deja el texto vacío.
            textoMensaje.text = ""

            // Solo la primera vez: si se gira la pantalla no se confirma otra vez.
            if (savedInstanceState == null) {
                presentador.confirmarRecibido(idLector, idReserva)
            }
        }
    }

    /** Lo llama el presentador cuando el préstamo se confirmó. */
    fun mostrarConfirmacion(prestamo: PrestamoConfirmado) {

        textoMensaje.text =
            prestamo.mensaje + ": “" + prestamo.titulo + "”.\n" +
            "Devuélvelo a más tardar el " + prestamo.fechaLimite + "."
    }

    /** Lo llama el presentador cuando algo falla: avisa y cierra la pantalla. */
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        finish()
    }
}
