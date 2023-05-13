package ir.enigma.app.ui.auth

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.Token
import ir.enigma.app.model.User
import ir.enigma.app.network.AddGroupRequest
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
        lateinit var me: User
        lateinit var token: String
    }

    var editUserState: MutableState<ApiResult<Any>> = mutableStateOf(ApiResult.Empty())

    fun checkForToken(context: Context) {
        viewModelScope.launch {
            startLading()
            val loadedToken = SharedPrefManager(context).getString(SharedPrefManager.KEY_TOKEN)
            if (loadedToken != null) {
                token = loadedToken
                setMe()
                success(Token(loadedToken))
            } else
                empty()
        }
    }

    fun setMe() {
        viewModelScope.launch {
            startLading()
            val result = userRepository.getUserInfo(token)
            if (result.status == ApiStatus.SUCCESS) {
                me = result.data!!.user
                Log.d(TAG, "setMe: $me")
                success(Token(token))
            } else
                error(result.message!!)
        }
    }

    fun login(
        context: Context,
        email: String,
        password: String
    ) {

        viewModelScope.launch {
            startLading()
            val result = userRepository.login(email, password)

            if (result.status == ApiStatus.SUCCESS) {
                token = "Token " + result.data!!.token
                saveToken(context, token)
                setMe()
            } else
                error(result.message!!)
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

    fun editProfile(name: String, picture_id: Int) {
        viewModelScope.launch {
            val editProfileInfo = userRepository.editProfile(token = token, name, picture_id)
            if (editProfileInfo.status == ApiStatus.SUCCESS) {
                me = User(id = me.id, name = name, email = me.email, iconId = picture_id)
            }
            editUserState.value = editProfileInfo
        }
    }

    private fun saveToken(context: Context, token: String) {
        viewModelScope.launch {
            SharedPrefManager(context).putString(SharedPrefManager.KEY_TOKEN, token)
        }
    }

}