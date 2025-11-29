package com.example.urbanstyle.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.urbanstyle.data.repository.AuthRepository

class LoginViewModel(private val repo: AuthRepository = AuthRepository()): ViewModel() {

    var uiState by mutableStateOf(LoginUIState())
        private set

    fun onUsernameChange(value:String){
        uiState =uiState.copy(username =value, error=null)
    }

    fun onpasswordChange(value:String){
        uiState =uiState.copy(password=value, error=null)
    }

    fun submit(onSuccess:(String) -> Unit){
        uiState = uiState.copy(isLoading=true, error =null)
        val oK =repo.login(uiState.username.trim(), uiState.password)


        uiState =uiState.copy(isLoading=false)

        if(oK != null) onSuccess(uiState.username.trim())
        else uiState =uiState.copy(error="Credenciales Invalidas")
    }
}