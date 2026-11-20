package com.example.cbtano5.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.R
import com.example.cbtano5.Modelo.dataPrestamo

class PrestamosAdaptador(
    private var listaPrestamos: List<dataPrestamo>
) : RecyclerView.Adapter<PrestamosAdaptador.PrestamoViewHolder>() {

    class PrestamoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgPortadaPrestamo: ImageView = itemView.findViewById(R.id.imgPortadaPrestamo)

        val txtTituloPrestamo: TextView = itemView.findViewById(R.id.txtTituloPrestamo)

        val txtAutorPrestamo: TextView = itemView.findViewById(R.id.txtAutorPrestamo)

        val txtFechaPrestamo: TextView = itemView.findViewById(R.id.txtFechaPrestamo)

        val txtFechaDevolucion: TextView = itemView.findViewById(R.id.txtFechaDevolucion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrestamoViewHolder {

        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_prestamo, parent, false)

        return PrestamoViewHolder(vista)
    }



    override fun onBindViewHolder(holder: PrestamoViewHolder, position: Int) {

        val prestamo = listaPrestamos[position]

        holder.txtTituloPrestamo.text = prestamo.tituloLibro

        holder.txtAutorPrestamo.text = "Autor no disponible"

        holder.txtFechaPrestamo.text = mostrarFecha(prestamo.fechaPrestamo)

        holder.txtFechaDevolucion.text = mostrarFecha(prestamo.fechaDevolucion)

        holder.imgPortadaPrestamo.setImageResource(android.R.drawable.ic_menu_gallery)
    }


    override fun getItemCount(): Int {
        return listaPrestamos.size
    }

    fun actualizarLista(nuevaLista: List<dataPrestamo>) {

        listaPrestamos = nuevaLista
        notifyDataSetChanged()
    }

    private fun mostrarFecha(fecha: String?): String {

        if (fecha.isNullOrBlank()) {
            return "No registrada"
        }

        val fechaSimple = fecha.take(10)
        val partes = fechaSimple.split("-")

        if (partes.size == 3) {
            return "${partes[2]}/${partes[1]}/${partes[0]}"
        }
        return fechaSimple
    }
}
