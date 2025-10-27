package com.example.urbanstyle.ui.login

data class LoginUIState (
    val username:String="",
    val password:String="",
    val isLoading:Boolean = false,
    val error:String? =null
)
