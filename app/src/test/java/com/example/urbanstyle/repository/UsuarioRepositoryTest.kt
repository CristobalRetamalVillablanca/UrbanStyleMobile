package com.example.urbanstyle.repository

import com.example.urbanstyle.data.dao.UsuarioDao
import com.example.urbanstyle.data.model.Usuario
import com.example.urbanstyle.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow // Necesario para simular los Flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

// -----------------------------------------------------------
// 1. EL FAKE DAO (Simulador de Base de Datos)
// -----------------------------------------------------------
class FakeUsuarioDao : UsuarioDao {

    // Nuestra "tabla" de base de datos en memoria
    private val usuariosDb = mutableListOf<Usuario>()

    override suspend fun insertarUsuario(usuario: Usuario) {
        usuariosDb.add(usuario)
    }

    override suspend fun update(usuario: Usuario) {
        // Buscamos el usuario por ID y lo reemplazamos (lógica simple para test)
        val index = usuariosDb.indexOfFirst { it.id == usuario.id }
        if (index != -1) {
            usuariosDb[index] = usuario
        }
    }

    override suspend fun findUserByEmailAndPassword(correo: String, contrasena: String): Usuario? {
        // Simulamos el Query de SQL buscando en la lista
        return usuariosDb.find { it.correo == correo && it.contrasena == contrasena }
    }

    // --- Implementación de los métodos Flow (Requeridos por la interfaz) ---

    override fun obtenerUsuarioPorCorreo(correo: String): Flow<Usuario?> = flow {
        // Emitimos el resultado inmediatamente
        val usuario = usuariosDb.find { it.correo == correo }
        emit(usuario)
    }

    override fun obtenerUsuarioPorId(id: Int): Flow<Usuario?> = flow {
        val usuario = usuariosDb.find { it.id == id }
        emit(usuario)
    }
}

// -----------------------------------------------------------
// 2. EL TEST DEL REPOSITORIO
// -----------------------------------------------------------
class UsuarioRepositoryTest {

    private lateinit var fakeDao: FakeUsuarioDao
    private lateinit var repository: UsuarioRepository

    @Before
    fun setUp() {
        // Inicializamos
        fakeDao = FakeUsuarioDao()
        repository = UsuarioRepository(fakeDao)
    }

    @Test
    fun `insertarUsuario agrega correctamente el usuario al DAO`() = runTest {
        // 1. Datos
        val usuario = Usuario(
            nombre = "Test User",
            fechaNacimiento = "1990-01-01",
            correo = "test@correo.com",
            contrasena = "123456",
            region = "RM",
            comuna = "Santiago",
            telefono = null,
            codigoDescuento = null
        )

        // 2. Acción
        repository.insertarUsuario(usuario)

        // 3. Verificación (Consultamos directamente al Fake para ver si se guardó)
        // Nota: Accedemos al método del DAO para verificar persistencia
        val guardado = fakeDao.findUserByEmailAndPassword("test@correo.com", "123456")

        assertNotNull("El usuario debería estar en la base de datos simulada", guardado)
        assertEquals("Test User", guardado?.nombre)
    }

    @Test
    fun `buscarUsuario retorna el usuario cuando las credenciales coinciden`() = runTest {
        // Given (Preparamos la base de datos con un usuario)
        val usuarioReal = Usuario(
            nombre = "Maria",
            fechaNacimiento = "1985-05-05",
            correo = "maria@gmail.com",
            contrasena = "passwordSegura",
            region = "Valparaiso",
            comuna = "Viña",
            telefono = "98765432",
            codigoDescuento = null
        )
        fakeDao.insertarUsuario(usuarioReal)

        // When (El repositorio busca)
        val resultado = repository.buscarUsuario("maria@gmail.com", "passwordSegura")

        // Then
        assertNotNull("Debería encontrar al usuario", resultado)
        assertEquals("Maria", resultado?.nombre)
        assertEquals("Viña", resultado?.comuna)
    }

    @Test
    fun `buscarUsuario retorna null cuando la contraseña es incorrecta`() = runTest {
        // Given
        val usuario = Usuario(
            nombre = "Pepe",
            fechaNacimiento = "2000-01-01",
            correo = "pepe@gmail.com",
            contrasena = "1234",
            region = "Sur",
            comuna = "Temuco",
            telefono = null,
            codigoDescuento = null
        )
        fakeDao.insertarUsuario(usuario)

        // When (Buscamos con contraseña mala)
        val resultado = repository.buscarUsuario("pepe@gmail.com", "9999") // Clave incorrecta

        // Then
        assertNull("No debería devolver usuario si la clave no coincide", resultado)
    }

    @Test
    fun `buscarUsuario retorna null cuando el correo no existe`() = runTest {
        // When (Buscamos alguien que no existe)
        val resultado = repository.buscarUsuario("inexistente@gmail.com", "1234")

        // Then
        assertNull(resultado)
    }
}