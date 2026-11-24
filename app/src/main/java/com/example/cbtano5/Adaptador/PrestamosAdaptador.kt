package com.example.cbtano5.Adaptador

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.cbtano5.R
import com.example.cbtano5.Modelo.dataLibro
import com.example.cbtano5.Modelo.dataPrestamo

class PrestamosAdaptador(
    private var listaPrestamos: List<dataPrestamo>
) : RecyclerView.Adapter<PrestamosAdaptador.PrestamoViewHolder>() {

    private val baseUrl = "https://biblioteccbtan5.4cuthh.com/bibliocbta5/"
    private val rutaPortadas = "upload/libros/principales/"

    // Libros del catálogo por id_libro, para completar portada y autor
    private var librosPorId: Map<Int, dataLibro> = emptyMap()

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

        val libro = librosPorId[prestamo.idLibro]

        holder.txtAutorPrestamo.text = prestamo.autor?.takeIf { it.isNotBlank() }
            ?: libro?.autores?.takeIf { it.isNotBlank() }
            ?: "Autor no disponible"

        holder.txtFechaPrestamo.text = mostrarFecha(prestamo.fechaPrestamo)

        holder.txtFechaDevolucion.text = mostrarFecha(prestamo.fechaDevolucion)

        val portadaRaw = listOfNotNull(
            prestamo.portada,
            prestamo.portadaLibro,
            prestamo.imagen,
            prestamo.imagenLibro,
            prestamo.libroPortada
        ).firstOrNull { it.isNotBlank() }?.trim() ?: ""

        val portadaCatalogo = libro?.portada?.trim() ?: ""

        // 1) Portada propia del préstamo  2) Portada del catálogo  3) Sin URL (placeholder)
        val urlImagen: String? = when {
            portadaRaw.startsWith("http") -> portadaRaw
            portadaRaw.startsWith("upload/") -> baseUrl + portadaRaw
            portadaRaw.isNotEmpty() -> baseUrl + rutaPortadas + portadaRaw
            portadaCatalogo.isNotEmpty() -> baseUrl + rutaPortadas + portadaCatalogo
            else -> null
        }

        // TEMPORAL DIAGNÓSTICO
        Log.d("DEBUG_PRESTAMOS", "---- Préstamo pos=$position ----")
        Log.d("DEBUG_PRESTAMOS", "tituloLibro=${prestamo.tituloLibro}")
        Log.d("DEBUG_PRESTAMOS", "portada=${prestamo.portada}")
        Log.d("DEBUG_PRESTAMOS", "portadaLibro=${prestamo.portadaLibro}")
        Log.d("DEBUG_PRESTAMOS", "imagen=${prestamo.imagen}")
        Log.d("DEBUG_PRESTAMOS", "imagenLibro=${prestamo.imagenLibro}")
        Log.d("DEBUG_PRESTAMOS", "libroPortada=${prestamo.libroPortada}")
        Log.d("DEBUG_PRESTAMOS", "portadaRaw='$portadaRaw'")
        Log.d("DEBUG_PRESTAMOS", "idLibro=${prestamo.idLibro} libroCatalogo=$libro")
        Log.d("DEBUG_PRESTAMOS", "urlImagen=$urlImagen (se carga: ${urlImagen != null})")

        Glide.with(holder.itemView.context)
            .load(urlImagen)
            .placeholder(R.drawable.cbta_ic_portada)
            .error(R.drawable.cbta_ic_portada)
            // TEMPORAL DIAGNÓSTICO
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("DEBUG_PRESTAMOS", "Glide onLoadFailed url=$model error=$e")
                    e?.rootCauses?.forEachIndexed { i, causa ->
                        Log.d("DEBUG_PRESTAMOS", "  causa ${i + 1}: $causa")
                    }
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("DEBUG_PRESTAMOS", "Glide carga OK url=$model")
                    return false
                }
            })
            .into(holder.imgPortadaPrestamo)
    }


    override fun getItemCount(): Int {
        return listaPrestamos.size
    }

    fun actualizarLista(nuevaLista: List<dataPrestamo>, librosCatalogo: Map<Int, dataLibro> = emptyMap()) {

        listaPrestamos = nuevaLista
        librosPorId = librosCatalogo
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
