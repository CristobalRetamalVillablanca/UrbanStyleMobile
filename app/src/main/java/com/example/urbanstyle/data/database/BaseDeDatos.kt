package com.example.urbanstyle.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.urbanstyle.data.dao.ProductoDao
import com.example.urbanstyle.data.dao.UsuarioDao
import com.example.urbanstyle.data.model.Producto
import com.example.urbanstyle.data.model.Usuario

@Database(entities = [Producto::class, Usuario::class], version = 2, exportSchema = false)
abstract class BaseDeDatos : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: BaseDeDatos? = null

        fun getDatabase(context: Context): BaseDeDatos {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatos::class.java,
                    "app_database"
                )
                    // Permite a Room recrear las tablas si se actualiza la versión de la BD.
                    // útil para desarrollo.
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
