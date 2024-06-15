package com.rafacraft.app_c2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rafacraft.app_c2.databinding.ActivityActualizarLibroBinding

class ActualizarLibroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityActualizarLibroBinding
    private lateinit var db: LibrosDatabaseHelper
    private var idLibro : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActualizarLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = LibrosDatabaseHelper(this)

        idLibro = intent.getIntExtra("id_libro", -1)
        if (idLibro == -1){
            finish()
            return
        }

        val libro = db.getIdLibro(idLibro)
        binding.etNombreActualizar.setText((libro.nombre))
        binding.etAutorActualizar.setText((libro.autor))
        binding.etPaginasActualizar.setText((libro.paginas.toString()))

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.imgIconActualizar.setOnClickListener {
            val nombreNuevo = binding.etNombreActualizar.text.toString()
            val autorNuevo = binding.etAutorActualizar.text.toString()
            val paginasNuevo = binding.etPaginasActualizar.text.toString().toIntOrNull()

            val libroActualizar = Libro (idLibro, nombreNuevo, autorNuevo, paginasNuevo)
            db.updateLibro(libroActualizar)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            Toast.makeText(this, "La nota se ha actualizado con exito", Toast.LENGTH_SHORT).show()
        }

    }
}