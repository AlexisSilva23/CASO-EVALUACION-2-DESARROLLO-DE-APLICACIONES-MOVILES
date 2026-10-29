package com.duoc.SafeMuni.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duoc.SafeMuni.model.Reporte
import com.duoc.SafeMuni.model.Usuario

@Database(
    entities = [Usuario::class, Reporte::class],
    version = 2, // Se incrementa la versión
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun reporteDao(): ReporteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "safemuni_database"
                )
                    .fallbackToDestructiveMigration() // <--- Evita que la app se cierre si cambia la BD
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}