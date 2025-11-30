package com.example.urbanstyle.repository

import com.example.urbanstyle.data.repository.ProductoRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProductoRepositoryTest {

    private lateinit var repository: ProductoRepository

    @Before
    fun setUp() {
        // Inicializamos el repositorio antes de cada test
        repository = ProductoRepository()
    }

    @Test
    fun `obtenerTodos devuelve el catalogo completo`() {
        // Ejecución
        val productos = repository.obtenerTodos()

        // Verificación
        // Sabemos que tu catálogo tiene 15 productos en total (8 tortas, 3 postres, 4 panadería)
        assertEquals("El catálogo debería tener 15 productos",15, productos.size )

        // Verificamos que el primero sea la Torta de Chocolate (TC001)
        assertEquals("TC001", productos[0].codigo)
        assertEquals("Torta de Chocolate", productos[0].nombre)
    }

    @Test
    fun `obtenerDestacados devuelve exactamente 4 productos especificos`() {
        // Ejecución
        val destacados = repository.obtenerDestacados()

        // Verificación
        assertEquals("Debería haber 4 productos destacados",4, destacados.size)

        // Verificamos (Chocolate, Vainilla, Cheesecake, Brownie)
        val codigosEsperados = listOf("TC001", "TC002", "PI001", "PG004")
        val codigosObtenidos = destacados.map { it.codigo }

        assertTrue( "Los destacados no son los correctos",codigosObtenidos.containsAll(codigosEsperados))
    }

    @Test
    fun `obtenerCategorias devuelve las categorias unicas sin repetir`() {
        // Ejecución
        val categorias = repository.obtenerCategorias()

        // Verificación
        // Esperamos "Tortas", "Postres", "Panadería"
        assertEquals(3, categorias.size)
        assertTrue(categorias.contains("Tortas"))
        assertTrue(categorias.contains("Postres"))
        assertTrue(categorias.contains("Panadería"))
    }

    @Test
    fun `porCodigo devuelve el producto correcto si existe`() {
        // Ejecución
        val producto = repository.porCodigo("TC006") // Torta Vegana

        // Verificación
        assertNotNull( "El producto debería existir",producto)
        assertEquals("Torta Vegana", producto?.nombre)
        assertEquals(48000, producto?.precio)
    }

    @Test
    fun `porCodigo devuelve null si el codigo no existe`() {
        // Ejecución
        val producto = repository.porCodigo("CODIGO_FALSO_123")

        // Verificación
        assertNull( "El producto no debería existir",producto)
    }
}