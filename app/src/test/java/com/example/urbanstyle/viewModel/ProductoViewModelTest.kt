package com.example.urbanstyle.viewModel

import com.example.urbanstyle.data.model.Producto
import com.example.urbanstyle.data.repository.ProductoRepository
import com.example.urbanstyle.viewmodel.ProductoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ProductoViewModelTest {

    // 1. REGLA: Configuramos el entorno para que el ViewModel crea que está en Android
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ProductoViewModel
    private lateinit var repository: ProductoRepository

    @Before
    fun setUp() {
        // Usamos el repositorio real
        repository = ProductoRepository()

        // Inyectamos el repositorio al ViewModel
        viewModel = ProductoViewModel(repository)
    }

    @Test
    fun `al iniciar, el ViewModel carga los 15 productos del repositorio`() {
        // Ejecución:
        val productos = viewModel.productos.value

        // Verificación
        // Sabemos que tu repositorio tiene 15 productos fijos
        assertEquals("La lista debería iniciar con 15 elementos", 15, productos.size)

        // Verificamos que el primero sea el correcto
        assertEquals("TC001", productos[0].codigo)
    }

    @Test
    fun `guardarProducto agrega un elemento nuevo a la lista local del ViewModel`() {
        // 1. Creamos un producto nuevo que no esté en el catálogo
        val nuevoProducto = Producto(
            codigo = "TEST001",
            nombre = "Producto de Prueba",
            categoria = "Test",
            precio = 9990,
            imagenRes = 0, // Un ID cualquiera
            descripcion = "Descripción de prueba"
        )

        // 2. Acción: Lo guardamos a través del ViewModel
        viewModel.guardarProducto(nuevoProducto)

        // 3. Verificación
        val listaActualizada = viewModel.productos.value

        // Ahora debería haber 16 productos (15 originales + 1 nuevo)
        assertEquals("Debería haber 16 productos", 16, listaActualizada.size)

        // Verificamos que el último de la lista sea el nuestro
        val ultimoProducto = listaActualizada.last()
        assertEquals("Producto de Prueba", ultimoProducto.nombre)
    }

    @Test
    fun `recargar restaura la lista original eliminando los cambios locales`() {
        // Agregamos un producto "basura" para ensuciar la lista
        val productoExtra = Producto("X", "X", "X", 0, 0, "X")
        viewModel.guardarProducto(productoExtra)

        // Verificamos que se ensució (ahora son 16)
        assertEquals(16, viewModel.productos.value.size)

        // Acción: Recargamos desde el repositorio
        viewModel.recargar()

        // Verificación
        val listaRestaurada = viewModel.productos.value

        // Debería volver a tener solo los 15 originales
        assertEquals("Recargar debería volver a 15 productos", 15, listaRestaurada.size)

        // El último elemento debería volver a ser el Brownie (PG004), no el producto "X"
        assertEquals("PG004", listaRestaurada.last().codigo)
    }
}

// --------------------------------------------------------------------------
// CLASE DE UTILIDAD
// --------------------------------------------------------------------------
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: kotlinx.coroutines.test.TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}