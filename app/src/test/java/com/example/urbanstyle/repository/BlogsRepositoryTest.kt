package com.example.urbanstyle.repository

import com.example.urbanstyle.data.repository.BlogRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BlogRepositoryTest {

    private lateinit var repository: BlogRepository

    @Before
    fun setUp() {
        // Preparamos una instancia
        repository = BlogRepository()
    }

    @Test
    fun `listar devuelve todos los posts y estan ordenados por fecha descendente`() {
        // Ejecución
        val lista = repository.listar()

        // Verificación de cantidad
        assertEquals("Deberían existir 3 posts en el blog", 3, lista.size)

        // Verificación de ORDENAMIENTO
        // El índice 0 debe ser el más nuevo ("2024-11-15")
        // El índice 2 debe ser el más antiguo ("2024-11-01")

        val fechaMasReciente = lista[0].fecha
        val fechaMasAntigua = lista[lastIndex(lista)].fecha // o lista[2]

        assertEquals("El primer post debe ser el del 15 de Noviembre", "2024-11-15", fechaMasReciente)
        assertEquals("El último post debe ser el del 01 de Noviembre", "2024-11-01", fechaMasAntigua)
    }

    @Test
    fun `porId encuentra el post correcto si el ID es valido`() {
        // Ejecución:
        val post = repository.porId(2)

        // Verificación
        assertNotNull("El post con id 2 debería existir", post)

        // Validamos datos específicos
        assertEquals("El autor debería ser Juan Pérez", "Pastelero Juan Pérez", post!!.autor)
        assertEquals("El ID debe coincidir", 2, post.id)
    }

    @Test
    fun `porId devuelve null si buscamos un ID inexistente`() {
        // Ejecución
        val post = repository.porId(999)

        // Verificación
        assertNull("No debería retornar nada para un ID desconocido", post)
    }

    @Test
    fun `verificar contenido del post tiene lineas de texto`() {
        // Un test extra para validar que el contenido no venga vacío
        val post = repository.porId(1)

        assertNotNull(post)
        // Tu primer post tiene 3 párrafos en la lista de contenido
        assertTrue("El contenido debería tener párrafos", post!!.contenido.isNotEmpty())
        assertEquals("Debería tener 3 párrafos", 3, post.contenido.size)
    }

    // Función auxiliar simple para obtener el último índice (opcional, solo por limpieza)
    private fun lastIndex(list: List<Any>) = list.size - 1
}