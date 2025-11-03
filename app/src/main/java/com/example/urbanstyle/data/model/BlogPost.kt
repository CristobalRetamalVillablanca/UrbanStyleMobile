package com.example.urbanstyle.data.model

data class BlogPost(
    val id: Int,
    val titulo: String,
    val fecha: String,     // "YYYY-MM-DD"
    val autor: String,
    val imagenRes: Int,    // drawable
    val extracto: String,
    val contenido: List<String>
)
