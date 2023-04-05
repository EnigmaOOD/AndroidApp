package ir.enigma.app.ui.auth

import androidx.compose.runtime.mutableStateOf

class AuthViewModel {
    val name = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val loading = mutableStateOf(false);


    fun login() {
        loading.value = true
    }

    fun register() {
        loading.value = true
    }

}