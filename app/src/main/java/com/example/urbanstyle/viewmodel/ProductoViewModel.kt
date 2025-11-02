package com.example.urbanstyle.viewmodel

import androidx.lifecycle.ViewModel
import com.example.urbanstyle.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductoViewModel : ViewModel() {

    // Estado observable (lista en memoria por ahora)
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    fun guardarProducto(producto: Producto) {
        _productos.value = _productos.value + producto
    }
}
