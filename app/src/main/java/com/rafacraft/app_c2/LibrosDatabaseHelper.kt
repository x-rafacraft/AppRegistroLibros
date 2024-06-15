package com.rafacraft.app_c2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LibrosDatabaseHelper (context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    companion object{
        private const val DATABASE_NAME = "libros.bd"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "libros"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "nombre"
        private const val COLUMN_AUTHOR = "autor"
        private const val COLUMN_PAGES = "paginas"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_AUTHOR TEXT, $COLUMN_PAGES INT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertLibro(libro : Libro){
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME, libro.nombre)
            put(COLUMN_AUTHOR, libro.autor)
            put(COLUMN_PAGES, libro.paginas)

        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllLibros() : List<Libro>{

        val listaLibros = mutableListOf<Libro>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val autor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
            val paginas = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PAGES))

            val libro = Libro (id, nombre, autor, paginas)
            listaLibros.add(libro)

        }

        cursor.close()
        db.close()
        return listaLibros
    }

    fun getIdLibro(idLibro : Int) : Libro{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $idLibro"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val autor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
        val paginas = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PAGES))

        cursor.close()
        db.close()
        return Libro(id, nombre , autor, paginas)
    }

    fun updateLibro (libro: Libro){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, libro.nombre)
            put(COLUMN_AUTHOR, libro.autor)
            put(COLUMN_PAGES, libro.paginas)
        }

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(libro.id.toString())
        db.update(TABLE_NAME, values , whereClause , whereArgs)
        db.close()
    }

    fun deleteLibro (idLibro : Int){
        val db = writableDatabase
        val whereClaus = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(idLibro.toString())
        db.delete(TABLE_NAME, whereClaus , whereArgs)
        db.close()
    }



}