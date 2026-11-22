package com.example.cbtano5.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cbtano5.Modelo.dataLibro
import com.example.cbtano5.R
import com.example.cbtano5.Vista.CatalogoLibrosActivity

class libroAdaptador(
    private val activity: CatalogoLibrosActivity,
    private var listaLibros: List<dataLibro>
) : RecyclerView.Adapter<libroAdaptador.LibroViewHolder>() {

    private val baseUrl = "https://biblioteccbtan5.4cuthh.com/bibliocbta5/"
    private val rutaPortadas = "upload/libros/principales/"

    class LibroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPortada: ImageView = itemView.findViewById(R.id.imgPortadaLibro)
        val tvTitulo: TextView = itemView.findViewById(R.id.txtTituloLibro)
        val tvAutor: TextView = itemView.findViewById(R.id.txtAutorLibro)
        val tvCategoria: TextView = itemView.findViewById(R.id.txtCategoriaLibro)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_libro_catalogo, parent, false)
        return LibroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = listaLibros[position]
        holder.tvTitulo.text = libro.titulo
        holder.tvAutor.text = libro.autores
        holder.tvCategoria.visibility = View.GONE

        val portada = libro.portada.trim()
        val urlImagen = when {
            portada.startsWith("http") -> portada
            portada.startsWith("upload/") -> baseUrl + portada
            else -> baseUrl + rutaPortadas + portada
        }

        Glide.with(holder.itemView.context)
            .load(urlImagen)
            .placeholder(R.drawable.cbta_ic_portada)
            .error(R.drawable.cbta_ic_portada)
            .into(holder.imgPortada)

        holder.itemView.setOnClickListener {
            activity.seleccionarLibro(libro)
        }
    }
    override fun getItemCount(): Int {
        return listaLibros.size
    }

    fun actualizarLista(nuevaLista: List<dataLibro>) {
        listaLibros = nuevaLista
        notifyDataSetChanged()
    }
}