package com.example.cbtano5.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.R
import com.example.cbtano5.Modelo.dataAviso
import com.example.cbtano5.Vista.InicioActivity

class AvisosAdaptador(val activity: InicioActivity, val lista: List<dataAviso>) :
    RecyclerView.Adapter<AvisosAdaptador.ViewHolder>() {
    // Guarda los componentes de cada fila
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTituloAviso: TextView = itemView.findViewById(R.id.txtTituloAviso)
        val txtMensajeAviso: TextView = itemView.findViewById(R.id.txtMensajeAviso)
    }

    // Crea una fila a partir del diseño item_aviso.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_aviso, parent, false)
        return ViewHolder(vista)
    }

    // Pone los datos del aviso en la fila
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val aviso = lista[position]
        holder.txtTituloAviso.text = aviso.titulo
        holder.txtMensajeAviso.text = aviso.mensaje
    }

    // Cantidad de avisos
    override fun getItemCount(): Int {
        return lista.size
    }
}