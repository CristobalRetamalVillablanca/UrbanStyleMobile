package com.example.urbanstyle.ui.map


import org.osmdroid.util.GeoPoint // Usamos GeoPoint de OSM


data class MapUiState(
    val userLocation: GeoPoint? = null, // Coordenadas del usuario (OSM usa GeoPoint)
    val storeLocation: GeoPoint = GeoPoint(-33.4489, -70.6693), // Ubicación del Local (Ej: Santiago)
    val isLoading: Boolean = true
)
