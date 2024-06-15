package com.rafacraft.app_c2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class LibrosAdaptador (
    private var libros : List<Libro>,
    context: Context) : RecyclerView.Adapter<LibrosAdaptador.LibroViewHolder>(){

        private val db : LibrosDatabaseHelper = LibrosDatabaseHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_libro,parent,false)
        return LibroViewHolder(view)
    }

    override fun getItemCount(): Int {
        return libros.size
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libros[position]
        holder.itemNombre.text = libro.nombre
        holder.itemAutor.text = libro.autor
        holder.itemPaginas.text = libro.paginas.toString()

        holder.ivActualizar.setOnClickListener {
            val intent = Intent(holder.itemView.context, ActualizarLibroActivity::class.java).apply {
                putExtra("id_libro", libro.id)
            }

            holder.itemView.context.startActivity(intent)
            Toast.makeText(
                holder.itemView.context,
                "El id del libro seleccionado es ${libro.id}",
                Toast.LENGTH_SHORT
            ).show()
        }

        holder.ivEliminar.setOnClickListener {
            db.deleteLibro(libro.id)
            refrescarLista(db.getAllLibros())
            Toast.makeText(holder.itemView.context, "Libro eliminado",Toast.LENGTH_SHORT).show()
        }
    }



    class LibroViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val itemNombre : TextView = itemView.findViewById(R.id.item_nombre)
        val itemAutor : TextView = itemView.findViewById(R.id.item_autor)
        val itemPaginas : TextView = itemView.findViewById(R.id.item_paginas)
        val ivActualizar : ImageView = itemView.findViewById(R.id.ivActualizar)
        val ivEliminar : ImageView = itemView.findViewById(R.id.ivEliminar)
    }

    fun refrescarLista (nuevoLibro : List<Libro>){
        libros = nuevoLibro
        notifyDataSetChanged()
    }
}