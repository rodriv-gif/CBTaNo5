package com.example.cbtano5.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cbtano5.Modelo.dataCategoria
import com.example.cbtano5.R
import com.example.cbtano5.Vista.CatalogoLibrosActivity

class categoriaAdaptador(
    private val activity: CatalogoLibrosActivity,
    private var listaCategorias: List<dataCategoria>
) : RecyclerView.Adapter<categoriaAdaptador.CategoriaViewHolder>() {

    class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.txtNombreCategoria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria_catalogo, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = listaCategorias[position]
        holder.tvNombre.text = categoria.nombre

        holder.itemView.setOnClickListener {
            activity.seleccionarCategoria(categoria)
        }
    }

    override fun getItemCount(): Int {
        return listaCategorias.size
    }

    fun actualizarLista(nuevaLista: List<dataCategoria>) {
        listaCategorias = nuevaLista
        notifyDataSetChanged()
    }
}