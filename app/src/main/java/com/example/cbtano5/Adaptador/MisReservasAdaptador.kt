package com.example.cbtano5.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cbtano5.Modelo.RetrofitCliente
import com.example.cbtano5.Modelo.dataReserva
import com.example.cbtano5.R
import com.example.cbtano5.Vista.MisReservasActivity

class MisReservasAdaptador(val activity: MisReservasActivity, val lista: List<dataReserva>) :
    RecyclerView.Adapter<MisReservasAdaptador.ReservaViewHolder>() {
    // Guarda los componentes de cada tarjeta de reserva
    class ReservaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitulo: TextView = itemView.findViewById(R.id.txtTitulo)
        val txtAutor: TextView = itemView.findViewById(R.id.txtAutor)
        val txtFechaReserva: TextView = itemView.findViewById(R.id.txtFechaReserva)
        val txtVigencia: TextView = itemView.findViewById(R.id.txtVigencia)
        val imgPortada: ImageView = itemView.findViewById(R.id.imgPortada)
        val btnCancelar: Button = itemView.findViewById(R.id.btnCancelar)
        val btnComprobante: Button = itemView.findViewById(R.id.btnComprobante)
    }

    // Crea una tarjeta a partir del diseño item_mis_reservas.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mis_reservas, parent, false)
        return ReservaViewHolder(view)
    }

    // Pone los datos de la reserva en la tarjeta
    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val reserva = lista[position]
        holder.txtTitulo.text = reserva.titulo
        holder.txtAutor.text = reserva.autor
        holder.txtFechaReserva.text = reserva.fechaReserva
        holder.txtVigencia.text = reserva.vigencia

        // Portada: se descarga con Glide; mientras carga o si falla se muestra un ícono
        val urlImagenCompleta = RetrofitCliente.BASE_URL + reserva.portada
        Glide.with(holder.itemView.context)
            .load(urlImagenCompleta)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.imgPortada)

        // Botón Cancelar: le avisa a la Activity cuál reserva se tocó
        holder.btnCancelar.setOnClickListener {
            activity.cancelarReserva(reserva)
        }

        // Botón Comprobante: le avisa a la Activity cuál reserva se tocó
        holder.btnComprobante.setOnClickListener {
            activity.verComprobante(reserva)
        }
    }

    // Cantidad de reservas que se muestran en la lista
    override fun getItemCount(): Int {
        return lista.size
    }
}