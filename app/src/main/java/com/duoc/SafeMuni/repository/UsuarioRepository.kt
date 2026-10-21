package com.duoc.SafeMuni

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.MessageDigest

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "safemuni.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USUARIOS = "usuarios"

        private const val ID = "id"
        private const val NOMBRE = "nombre"
        private const val APELLIDO = "apellido"
        private const val CORREO = "correo"
        private const val USUARIO = "usuario"
        private const val PASSWORD = "password"
        private const val ROL = "rol"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val crearTablaUsuarios = """
            CREATE TABLE $TABLE_USUARIOS (
                $ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $NOMBRE TEXT NOT NULL,
                $APELLIDO TEXT NOT NULL,
                $CORREO TEXT NOT NULL UNIQUE,
                $USUARIO TEXT NOT NULL UNIQUE,
                $PASSWORD TEXT NOT NULL,
                $ROL TEXT NOT NULL DEFAULT 'Usuario'
            )
        """.trimIndent()

        db.execSQL(crearTablaUsuarios)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }

    private fun hashPassword(password: String): String {

        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())

        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

    fun registrarUsuario(
        nombre: String,
        apellido: String,
        correo: String,
        usuario: String,
        password: String
    ): Boolean {

        val db = writableDatabase

        val valores = ContentValues().apply {
            put(NOMBRE, nombre)
            put(APELLIDO, apellido)
            put(CORREO, correo.lowercase())
            put(USUARIO, usuario.lowercase())
            put(PASSWORD, hashPassword(password))
            put(ROL, "Usuario")
        }

        return try {

            val resultado = db.insert(
                TABLE_USUARIOS,
                null,
                valores
            )

            resultado != -1L

        } catch (e: Exception) {

            false
        }
    }

    fun validarUsuario(
        usuario: String,
        password: String
    ): Boolean {

        val db = readableDatabase

        val cursor = db.query(
            TABLE_USUARIOS,
            arrayOf(ID),
            "$USUARIO = ? AND $PASSWORD = ?",
            arrayOf(
                usuario.lowercase(),
                hashPassword(password)
            ),
            null,
            null,
            null
        )

        val existe = cursor.count > 0

        cursor.close()

        return existe
    }
}