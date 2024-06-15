package com.rafacraft.app_c2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rafacraft.app_c2.databinding.ActivityAgregarLibroBinding

class AgregarLibroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarLibroBinding
    private lateinit var db: LibrosDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = LibrosDatabaseHelper(this)

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.imgIcon.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val autor = binding.etAutor.text.toString()
            val paginasText = binding.etPaginas.text.toString()

            // Validar que los campos no estén vacíos
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese el nombre del libro", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (autor.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese el autor del libro", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (paginasText.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese el número de páginas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar que el número de páginas sea un número válido
            val paginas = paginasText.toIntOrNull()
            if (paginas == null || paginas <= 0) {
                Toast.makeText(this, "Por favor, ingrese un número válido de páginas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Si todas las validaciones pasan, agregar el libro a la base de datos
            val libro = Libro(0, nombre, autor, paginas)
            db.insertLibro(libro)
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finishAffinity()
            Toast.makeText(applicationContext, "Se agregó el libro", Toast.LENGTH_SHORT).show()
        }
    }
}
