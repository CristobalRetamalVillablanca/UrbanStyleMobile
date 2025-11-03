package com.example.urbanstyle.data.repository

import com.example.urbanstyle.R
import com.example.urbanstyle.data.model.BlogPost

class BlogRepository {

    private val posts = listOf(
        BlogPost(
            id = 1,
            titulo = "El Arte de la Repostería Chilena",
            fecha = "2024-11-15",
            autor = "Chef María González",
            imagenRes = R.drawable.blog_reposteria,
            extracto = "Descubre la rica tradición de la repostería chilena y sus secretos mejor guardados.",
            contenido = listOf(
                "La repostería chilena es una fusión de tradiciones indígenas, españolas y europeas que ha evolucionado a lo largo de los siglos. Desde las famosas tortas de mil hojas hasta los clásicos pasteles de choclo, cada receta cuenta una historia.",
                "El manjar, la harina tostada, los frutos secos nativos y las frutas de temporada son la base de muchas de nuestras recetas tradicionales.",
                "Hoy combinamos estas tradiciones con técnicas modernas de repostería, creando productos que honran el pasado mientras innovamos para el futuro."
            )
        ),
        BlogPost(
            id = 2,
            titulo = "Cómo Elegir la Torta Perfecta para tu Evento",
            fecha = "2024-11-08",
            autor = "Pastelero Juan Pérez",
            imagenRes = R.drawable.blog_torta_perfecta,
            extracto = "Guía completa para seleccionar la torta ideal según el tipo de celebración.",
            contenido = listOf(
                "Para matrimonios, recomendamos tortas de varios pisos con rellenos suaves y decoración elegante.",
                "Para cumpleaños infantiles, las tortas temáticas de vainilla o chocolate siempre triunfan.",
                "Considera el número de invitados, alergias y preferencias al elegir sabores y tamaños."
            )
        ),
        BlogPost(
            id = 3,
            titulo = "Tips para Decoración de Tortas en Casa",
            fecha = "2024-11-01",
            autor = "Equipo Mil Sabores",
            imagenRes = R.drawable.blog_decoracion,
            extracto = "Aprende técnicas simples para decorar tortas como un profesional desde tu hogar.",
            contenido = listOf(
                "Ten a mano mangas, boquillas básicas y una espátula angular.",
                "Trabaja siempre con la torta fría para lograr terminaciones prolijas.",
                "Empieza por un 'crumb coat' fino, refrigera y luego aplica la capa final."
            )
        )
    )

    fun listar(): List<BlogPost> = posts.sortedByDescending { it.fecha }
    fun porId(id: Int): BlogPost? = posts.find { it.id == id }
}
