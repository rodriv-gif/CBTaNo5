package com.example.cbtano5.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cbtano5.Modelo.Adeudo
import com.example.cbtano5.Modelo.RetrofitCliente
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

        // Arriba va el titulo del libro; abajo dice si es retraso o reposicion
        holder.concepto.text = adeudo.titulo

        holder.dias.text = contexto.getString(
            R.string.cbta_adeudo_dias,
            adeudo.diasRetraso
        )

        holder.monto.text = contexto.getString(
            R.string.cbta_adeudo_monto,
            adeudo.adeudo
        )

        // Portada: se descarga con Glide; mientras carga o si falla se queda el cuadro del wireframe
        val urlImagenCompleta = RetrofitCliente.BASE_URL + "upload/libros/principales/" + adeudo.portada
        Glide.with(holder.itemView.context)
            .load(urlImagenCompleta)
            .placeholder(R.drawable.cbta_ic_portada)
            .error(R.drawable.cbta_ic_portada)
            .into(holder.portada)
    }

    class AdeudoViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {

        val portada: ImageView = vista.findViewById(R.id.portada_adeudo)
        val concepto: TextView = vista.findViewById(R.id.concepto_adeudo)
        val dias: TextView = vista.findViewById(R.id.dias_adeudo)
        val monto: TextView = vista.findViewById(R.id.monto_adeudo)
    }
}

