package com.example.urbanstyle.ui.map


import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.osmdroid.util.GeoPoint // Usamos GeoPoint de OSM

class MapViewModel(application: Application) : AndroidViewModel(application) {

    // Cliente para obtener ubicación de Google Play Services
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    @SuppressLint("MissingPermission") // Se asume que el permiso se pide en la UI
    fun getCurrentLocation() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                val locationResult: Location? = fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    null
                ).await() ?: fusedLocationClient.lastLocation.await()

                // Si obtenemos coordenadas, actualizamos el estado
                locationResult?.let {
                    _uiState.value = _uiState.value.copy(
                        userLocation = GeoPoint(it.latitude, it.longitude),
                        isLoading = false
                    )
                } ?: run {
                    // Si llega nulo (raro si hay permisos), quitamos la carga
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }

            } catch (e: Exception) {
                // Si falla (GPS apagado, sin red, etc.), dejamos de cargar
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}