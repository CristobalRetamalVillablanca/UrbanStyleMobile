package com.example.urbanstyle.data.repository

import com.example.urbanstyle.R
import com.example.urbanstyle.data.model.Producto

class ProductoRepository {

    // 🔵 ÚNICA fuente de datos del repositorio
    private val catalogo: List<Producto> = listOf(
        // TORTAS
        Producto(
            codigo = "TC001",
            nombre = "Torta de Chocolate",
            categoria = "Tortas",
            precio = 45000,
            imagenRes = R.drawable.torta_chocolate,
            descripcion = "Bizcocho húmedo de cacao con relleno de ganache y cobertura artesanal."
        ),
        Producto(
            codigo = "TC002",
            nombre = "Torta de Vainilla",
            categoria = "Tortas",
            precio = 42000,
            imagenRes = R.drawable.torta_vainilla,
            descripcion = "Clásica torta de vainilla con crema pastelera y frutillas frescas."
        ),
        Producto(
            codigo = "TC003",
            nombre = "Torta de Frutas",
            categoria = "Tortas",
            precio = 46000,
            imagenRes = R.drawable.torta_frutas,
            descripcion = "Torta ligera con base de bizcocho y frutas de estación."
        ),
        Producto(
            codigo = "TC004",
            nombre = "Torta de Manjar",
            categoria = "Tortas",
            precio = 47000,
            imagenRes = R.drawable.torta_manjar,
            descripcion = "Torta tradicional chilena con manjar casero y hojarasca crujiente."
        ),
        Producto(
            codigo = "TC005",
            nombre = "Torta de Naranja",
            categoria = "Tortas",
            precio = 44000,
            imagenRes = R.drawable.torta_naranja,
            descripcion = "Bizcocho cítrico relleno de crema de naranja natural."
        ),
        Producto(
            codigo = "TC006",
            nombre = "Torta Vegana",
            categoria = "Tortas",
            precio = 48000,
            imagenRes = R.drawable.torta_vegana,
            descripcion = "Torta elaborada sin ingredientes de origen animal, con cacao orgánico."
        ),
        Producto(
            codigo = "TC007",
            nombre = "Torta de Cumpleaños",
            categoria = "Tortas",
            precio = 50000,
            imagenRes = R.drawable.torta_cumpleanos,
            descripcion = "Colorida torta de cumpleaños con relleno de vainilla y buttercream."
        ),
        Producto(
            codigo = "TC008",
            nombre = "Torta de Boda",
            categoria = "Tortas",
            precio = 85000,
            imagenRes = R.drawable.torta_boda,
            descripcion = "Elegante torta nupcial con varias capas y detalles florales."
        ),

        // POSTRES
        Producto(
            codigo = "PI001",
            nombre = "Cheesecake",
            categoria = "Postres",
            precio = 5500,
            imagenRes = R.drawable.cheesecake,
            descripcion = "Cheesecake artesanal con base de galletas y coulis de frutilla."
        ),
        Producto(
            codigo = "PI002",
            nombre = "Tiramisú",
            categoria = "Postres",
            precio = 6000,
            imagenRes = R.drawable.tiramisu,
            descripcion = "Clásico postre italiano con mascarpone, café y cacao."
        ),
        Producto(
            codigo = "PI003",
            nombre = "Mousse de Chocolate",
            categoria = "Postres",
            precio = 5800,
            imagenRes = R.drawable.mousse_chocolate,
            descripcion = "Mousse aireado de chocolate belga con cobertura de cacao amargo."
        ),

        // PANADERÍA
        Producto(
            codigo = "PG001",
            nombre = "Galletas de Avena",
            categoria = "Panadería",
            precio = 3500,
            imagenRes = R.drawable.galletas_avena,
            descripcion = "Galletas caseras con avena, miel y chips de chocolate."
        ),
        Producto(
            codigo = "PG002",
            nombre = "Empanada de Manzana",
            categoria = "Panadería",
            precio = 3200,
            imagenRes = R.drawable.empanada_manzana,
            descripcion = "Empanadas dulces rellenas con compota de manzana y canela."
        ),
        Producto(
            codigo = "PG003",
            nombre = "Pan Sin Gluten",
            categoria = "Panadería",
            precio = 2500,
            imagenRes = R.drawable.pan_sin_gluten,
            descripcion = "Pan artesanal sin gluten, suave y con semillas naturales."
        ),
        Producto(
            codigo = "PG004",
            nombre = "Brownie",
            categoria = "Panadería",
            precio = 3800,
            imagenRes = R.drawable.brownie,
            descripcion = "Brownie denso y húmedo con nueces tostadas."
        )
    )

    // Compatibilidad (si en alguna parte llaman a esto)
    fun obtenerProductos(): List<Producto> = catalogo

    // Catálogo completo
    fun obtenerTodos(): List<Producto> = catalogo

    // Destacados (elige algunos del catálogo)
    fun obtenerDestacados(): List<Producto> = listOf(
        catalogo.first { it.codigo == "TC001" }, // Chocolate
        catalogo.first { it.codigo == "TC002" }, // Vainilla
        catalogo.first { it.codigo == "PI001" }, // Cheesecake
        catalogo.first { it.codigo == "PG004" }  // Brownie
    )

    // Categorías únicas
    fun obtenerCategorias(): List<String> = catalogo.map { it.categoria }.distinct()

    // Búsqueda por código (segura)
    fun porCodigo(codigo: String): Producto? = catalogo.find { it.codigo == codigo }
}
