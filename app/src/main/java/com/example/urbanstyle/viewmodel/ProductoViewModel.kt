package com.example.urbanstyle.viewmodel

import androidx.lifecycle.ViewModel
import com.example.urbanstyle.data.model.Producto
import com.example.urbanstyle.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductoViewModel(
    private val repo: ProductoRepository = ProductoRepository()
) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    init {
        // ✅ carga inicial del catálogo para que ProductosScreen no quede vacío
        _productos.value = repo.obtenerTodos()
    }

    fun guardarProducto(p: Producto) {
        _productos.value = _productos.value + p
    }

    fun recargar() {
        _productos.value = repo.obtenerTodos()
    }
}
