package com.example.cbtano5.Vista

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cbtano5.Modelo.ReservaRealizada
import com.example.cbtano5.Presentador.clsReservaRealizadaPresentador
import com.example.cbtano5.R

class Reserva_Realizada : AppCompatActivity() {

    companion object {

        // Opción 1 (la buena): mandar el id del lector y de la reserva.
        // La pantalla pide los datos a obtener_reserva_realizada.php.
        //   val intent = Intent(this, Reserva_Realizada::class.java)
        //   intent.putExtra(Reserva_Realizada.EXTRA_ID_LECTOR, id)
        //   intent.putExtra(Reserva_Realizada.EXTRA_ID_RESERVA, idReserva)
        //   startActivity(intent)
        const val EXTRA_ID_LECTOR = "id"
        const val EXTRA_ID_RESERVA = "id_reserva"

        // Opción 2: mandar directamente el título y la fecha de recogida.
        //   intent.putExtra(Reserva_Realizada.EXTRA_TITULO, "Cien años de soledad")
        //   intent.putExtra(Reserva_Realizada.EXTRA_VIGENCIA, "19/11/2026")
        const val EXTRA_TITULO = "titulo"
        const val EXTRA_VIGENCIA = "vigencia_texto"
    }

    private lateinit var textoMensaje: TextView
    private lateinit var textoVigencia: TextView

    private lateinit var presentador: clsReservaRealizadaPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserva_realizada)

        ajustarBarrasDelSistema(
            findViewById(R.id.raiz),
            findViewById(R.id.encabezado)
        )

        textoMensaje = findViewById(R.id.texto_mensaje)
        textoVigencia = findViewById(R.id.texto_vigencia)

        findViewById<Button>(R.id.btn_aceptar).setOnClickListener {
            finish()
        }

        presentador = clsReservaRealizadaPresentador(this)

        val idLector = intent.getIntExtra(EXTRA_ID_LECTOR, 0)
        val idReserva = intent.getIntExtra(EXTRA_ID_RESERVA, 0)

        if (idLector > 0 && idReserva > 0) {

            // Mientras llegan los datos se dejan los textos vacíos.
            textoMensaje.text = ""
            textoVigencia.text = ""

            presentador.cargarReserva(idLector, idReserva)

        } else {

            // Sin ids: se usan los extras o, si no hay, los del wireframe.
            val titulo = intent.getStringExtra(EXTRA_TITULO)
                ?: "Cien años de soledad"

            val fecha = intent.getStringExtra(EXTRA_VIGENCIA)
                ?: "05/11/2025"

            mostrarDatos(titulo, formatearFecha(fecha))
        }
    }

    /** Lo llama el presentador cuando llegan los datos de la API. */
    fun mostrarReserva(reserva: ReservaRealizada) {
        mostrarDatos(reserva.titulo, formatearFecha(reserva.vigencia))
    }

    private fun mostrarDatos(titulo: String, fechaRecogida: String) {

        textoMensaje.text =
            getString(R.string.cbta_reserva_mensaje, titulo)

        textoVigencia.text = fechaRecogida
    }

    /**
     * Deja la fecha en dd/MM/yyyy.
     * Si llega como yyyy-MM-dd (o yyyy-MM-dd HH:mm:ss) se reacomoda con split("-"),
     * sin Date ni SimpleDateFormat para no desfasar un día por la zona horaria.
     * Si ya viene como dd/MM/yyyy se deja igual.
     */
    private fun formatearFecha(fecha: String): String {
        val fechaSimple = fecha.trim().take(10)
        val partes = fechaSimple.split("-")

        if (partes.size == 3 && partes[0].length == 4) {
            return partes[2] + "/" + partes[1] + "/" + partes[0]
        }
        return fechaSimple
    }

    /** Lo llama el presentador cuando algo falla: avisa y cierra la pantalla. */
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        finish()
    }
}
