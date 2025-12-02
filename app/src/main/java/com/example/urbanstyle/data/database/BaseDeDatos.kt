package com.example.urbanstyle.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.urbanstyle.data.dao.ProductoDao
import com.example.urbanstyle.data.dao.UsuarioDao
import com.example.urbanstyle.data.model.Producto
import com.example.urbanstyle.data.model.Usuario

@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class BaseDeDatos : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: BaseDeDatos? = null

        fun getDatabase(context: Context): BaseDeDatos {
            // Patrón Singleton para no abrir múltiples conexiones a la vez
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatos::class.java,
                    "huerto_hogar_db" // Nombre del archivo de la BD en el celular
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
