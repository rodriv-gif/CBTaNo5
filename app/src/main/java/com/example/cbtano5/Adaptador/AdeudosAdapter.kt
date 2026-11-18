package com.example.cbtano5.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.Modelo.Adeudo
import com.example.cbtano5.R

/** Pinta la lista de adeudos (libros vencidos y libros a reponer). */
class AdeudosAdapter : RecyclerView.Adapter<AdeudosAdapter.AdeudoViewHolder>() {

    private val adeudos = mutableListOf<Adeudo>()

    /** Reemplaza la lista completa y vuelve a dibujar. */
    fun actualizar(nuevos: List<Adeudo>) {
        adeudos.clear()
        adeudos.addAll(nuevos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdeudoViewHolder {

        val vista = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_adeudo_lista, parent, false)

        return AdeudoViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return adeudos.size
    }

    override fun onBindViewHolder(
        holder: AdeudoViewHolder,
        position: Int
    ) {

        val adeudo = adeudos[position]
        val contexto = holder.itemView.context

        holder.concepto.text = adeudo.concepto

        holder.dias.text = contexto.getString(
            R.string.cbta_adeudo_dias,
            adeudo.diasRetraso
        )

        holder.monto.text = contexto.getString(
            R.string.cbta_adeudo_monto,
            adeudo.adeudo
        )

        // TODO (cuando conectes la API): cargar aquí la portada con adeudo.portada.
        // Mientras tanto se queda el cuadro con X del wireframe.
    }

    class AdeudoViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {

        val portada: ImageView = vista.findViewById(R.id.portada_adeudo)
        val concepto: TextView = vista.findViewById(R.id.concepto_adeudo)
        val dias: TextView = vista.findViewById(R.id.dias_adeudo)
        val monto: TextView = vista.findViewById(R.id.monto_adeudo)
    }
}

