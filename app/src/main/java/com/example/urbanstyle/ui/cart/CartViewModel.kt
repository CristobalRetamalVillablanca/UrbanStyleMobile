package com.example.urbanstyle.ui.cart

import androidx.lifecycle.ViewModel
import com.example.urbanstyle.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.util.Locale

class CartViewModel : ViewModel() {

    // Estado: Lista de productos en el carrito
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // Estado: Precio Total (Calculado automáticamente)
    private val _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int> = _totalPrice.asStateFlow()

    // Agregar producto (o aumentar cantidad si ya existe)
    fun agregarProducto(producto: Producto) {
        _cartItems.update { currentItems ->
            val existingItem = currentItems.find { it.producto.codigo == producto.codigo }

            if (existingItem != null) {
                // Si ya existe, creamos una nueva lista actualizando la cantidad de ese item
                currentItems.map {
                    if (it.producto.codigo == producto.codigo) {
                        it.copy(cantidad = it.cantidad + 1)
                    } else {
                        it
                    }
                }
            } else {
                // Si no existe, lo agregamos con cantidad 1
                currentItems + CartItem(producto, 1)
            }
        }
        recalcularTotal()
    }

    // Disminuir cantidad o eliminar si es 0
    fun removerProducto(producto: Producto) {
        _cartItems.update { currentItems ->
            val existingItem = currentItems.find { it.producto.codigo == producto.codigo } ?: return@update currentItems

            if (existingItem.cantidad > 1) {
                currentItems.map {
                    if (it.producto.codigo == producto.codigo) {
                        it.copy(cantidad = it.cantidad - 1)
                    } else {
                        it
                    }
                }
            } else {
                // Si la cantidad es 1 y restamos, lo borramos de la lista
                currentItems.filter { it.producto.codigo != producto.codigo }
            }
        }
        recalcularTotal()
    }

    // Vaciar todo
    fun limpiarCarrito() {
        _cartItems.value = emptyList()
        recalcularTotal()
    }

    private fun recalcularTotal() {
        // Suma: Precio * Cantidad de cada item
        _totalPrice.value = _cartItems.value.sumOf { it.producto.precio * it.cantidad }
    }

    // Utilidad para formatear moneda (Pesos Chilenos)
    fun formatearPrecio(precio: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
        return format.format(precio)
    }
}