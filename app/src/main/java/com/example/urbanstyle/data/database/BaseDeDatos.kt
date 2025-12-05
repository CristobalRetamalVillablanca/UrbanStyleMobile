package com.example.urbanstyle.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.urbanstyle.data.dao.UsuarioDao
import com.example.urbanstyle.data.model.Usuario

@Database(
    entities = [Usuario::class],
    version = 2,               // 🔹 AUMENTAR VERSIÓN AL CAMBIAR EL ESQUEMA
    exportSchema = false
)
abstract class BaseDeDatos : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: BaseDeDatos? = null

        fun getDatabase(context: Context): BaseDeDatos {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatos::class.java,
                    "huerto_hogar_db"  // Archivo real de la BD en el emulador
                )
                    // 🔹 Si cambia el esquema (por ejemplo: nueva columna), recrear BD
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
