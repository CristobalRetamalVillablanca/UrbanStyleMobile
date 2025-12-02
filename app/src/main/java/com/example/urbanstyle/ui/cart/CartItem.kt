package com.example.urbanstyle.ui.cart

import com.example.urbanstyle.data.model.Producto

data class CartItem(
    val producto: Producto,
    val cantidad: Int
)