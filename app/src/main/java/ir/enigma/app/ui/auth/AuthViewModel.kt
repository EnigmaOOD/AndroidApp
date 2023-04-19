package ir.enigma.app.ui.auth

import android.content.Context
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.Token
import ir.enigma.app.model.User
import ir.enigma.app.repostitory.UserRepository
import ir.enigma.app.ui.ApiViewModel
import ir.enigma.app.util.SharedPrefManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ApiViewModel<Token?>() {

    companion object {
        const val TAG = "AuthViewModel"
        var me: User? = null
        var token: String? = null
    }

    fun checkForToken(context: Context) {
        viewModelScope.launch {
            startLading()
            val loadedToken = SharedPrefManager(context).getString(SharedPrefManager.KEY_TOKEN)
            if (loadedToken != null) {
                //todo: get me
                token = loadedToken
                success(Token(loadedToken))
            } else
                empty()
        }
    }


    fun login(
        email: String,
        password: String
    ) {

        callApi {
            userRepository.login(email, password)
        }

    }

    fun register(
        context: Context,
        name: String,
        email: String,
        iconId: Int,
        password: String
    ) {
        viewModelScope.launch {
            startLading()
            var user = User(
                id = -1,
                name = name,
                email = email,
                iconId = iconId,
                password = password,
            )
            val result = userRepository.register(user)
            if (result.status == ApiStatus.SUCCESS) {
                user = result.data!!
                state.value = userRepository.login(email, password)
                if (state.value.status == ApiStatus.SUCCESS) {
                    me = user
                    token = state.value.data!!.token
                    saveToken(context, state.value.data!!.token)
                }
            } else
                error(result.message!!)
        }
    }

    private fun saveToken(context: Context, token: String) {
        viewModelScope.launch {
            SharedPrefManager(context).putString(SharedPrefManager.KEY_TOKEN, token)
        }
    }

}