package com.example.urbanstyle.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.urbanstyle.data.model.Usuario

@Dao
interface UsuarioDao {
    // Registrar usuario: Si el ID choca, lo reemplaza (aunque con autogenerate es raro)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: Usuario)

    // Login: Busca un usuario que tenga ESE correo y ESA contraseña
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contrasena = :contrasena LIMIT 1")
    suspend fun login(correo: String, contrasena: String): Usuario?

    // Validar: Chequear si el correo ya existe para no repetir registros
    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun obtenerPorCorreo(correo: String): Usuario?

    // actualizar comentario
    @Query("UPDATE usuarios SET comentarioLogin = :comentario WHERE correo = :correo")
    suspend fun actualizarComentarioLogin(correo: String, comentario: String)
}
