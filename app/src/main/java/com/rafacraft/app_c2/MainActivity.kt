package com.rafacraft.app_c2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafacraft.app_c2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var db :LibrosDatabaseHelper
    private lateinit var librosAdaptador :LibrosAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = LibrosDatabaseHelper(this)

        librosAdaptador = LibrosAdaptador(db.getAllLibros(), this)

        binding.librosRv.layoutManager = LinearLayoutManager(this)
        binding.librosRv.adapter = librosAdaptador

        binding.BTNAgregarLibro.setOnClickListener {
            startActivity(Intent(applicationContext, AgregarLibroActivity::class.java))
        }
    }
}