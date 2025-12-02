package com.example.urbanstyle.data.repository

import com.example.urbanstyle.data.model.Post
import com.example.urbanstyle.data.remote.RetrofitInstance

// Este repositorio se encarga de acceder a los datos usando Retrofit
class PostRepository {
    // Función que obtiene los posts desde la API
    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }
}