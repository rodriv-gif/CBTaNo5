package com.example.cbtano5.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.Modelo.Aviso
import com.example.cbtano5.R

/**
 * Pinta la lista de avisos. El color de cada tarjeta depende del tipo:
 *   - "Devoluciones"            -> verde
 *   - "Nuevo libro"             -> azul
 *   - cualquier otro (Horario,
 *     Reserva por vencer, etc.) -> naranja
 */
class AvisosAdapter : RecyclerView.Adapter<AvisosAdapter.AvisoViewHolder>() {

    private val avisos = mutableListOf<Aviso>()

    /** Reemplaza la lista completa y vuelve a dibujar. */
    fun actualizar(nuevos: List<Aviso>) {
        avisos.clear()
        avisos.addAll(nuevos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvisoViewHolder {

        val vista = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_aviso_notificacion, parent, false)

        return AvisoViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return avisos.size
    }

    override fun onBindViewHolder(
        holder: AvisoViewHolder,
        position: Int
    ) {

        val aviso = avisos[position]
        val estilo = estiloPara(aviso.titulo)

        holder.titulo.text = aviso.titulo
        holder.mensaje.text = aviso.mensaje
        holder.fecha.text = aviso.fecha

        holder.tarjeta.setBackgroundResource(estilo.fondo)

        holder.icono.setColorFilter(
            ContextCompat.getColor(holder.itemView.context, estilo.colorIcono)
        )
    }

    // ----------------------------------------------------------------

    private class EstiloAviso(
        @DrawableRes val fondo: Int,
        @ColorRes val colorIcono: Int
    )

    private fun estiloPara(titulo: String): EstiloAviso {

        val tipo = titulo.lowercase()

        return when {

            tipo.contains("devol") ->
                EstiloAviso(
                    R.drawable.cbta_bg_aviso_verde,
                    R.color.cbta_aviso_verde_icono
                )

            tipo.contains("nuevo") ->
                EstiloAviso(
                    R.drawable.cbta_bg_aviso_azul,
                    R.color.cbta_aviso_azul_icono
                )

            else ->
                EstiloAviso(
                    R.drawable.cbta_bg_aviso_naranja,
                    R.color.cbta_aviso_naranja_icono
                )
        }
    }

    class AvisoViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {

        val tarjeta: View = vista.findViewById(R.id.tarjeta_aviso)
        val icono: ImageView = vista.findViewById(R.id.icono_aviso)
        val titulo: TextView = vista.findViewById(R.id.titulo_aviso)
        val fecha: TextView = vista.findViewById(R.id.fecha_aviso)
        val mensaje: TextView = vista.findViewById(R.id.mensaje_aviso)
    }
}

