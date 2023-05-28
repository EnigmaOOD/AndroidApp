package ir.enigma.app.ui.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.Token
import ir.enigma.app.model.User
import ir.enigma.app.repostitory.UserRepository
import ir.enigma.app.ui.ApiViewModel
import ir.enigma.app.util.LogType
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.SharedPrefManager
import ir.enigma.app.util.StructureLayer
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
        const val EMAIL_VERIFICATION =
            "ایمیل تایید برای شما ارسال شد. لطفا پس از تایید ورود را کلیک کنید"
    }

    var editUserState: MutableState<ApiResult<Any>> = mutableStateOf(ApiResult.Empty())

    fun checkForToken(sharedPrefManager: SharedPrefManager) {
        viewModelScope.launch {
            startLoading()
            val loadedToken = sharedPrefManager.getString(SharedPrefManager.KEY_TOKEN)
            if (loadedToken != null) {
                MyLog.log(
                    StructureLayer.ViewModel,
                    "AuthViewModel",
                    "checkForToken",
                    LogType.Info,
                    "loadedToken != null"
                )
                token = loadedToken
                setMe()
                success(Token(loadedToken))
            } else {
                MyLog.log(
                    StructureLayer.ViewModel,
                    "AuthViewModel",
                    "checkForToken",
                    LogType.Info,
                    "loadedToken == null"
                )
                empty()
            }
        }
    }

    private fun setMe() {
        viewModelScope.launch {
            startLoading()
            val result = userRepository.getUserInfo(token)
            if (result.status == ApiStatus.SUCCESS) {

                me = result.data!!.user
                success(Token(token))
                MyLog.log(
                    StructureLayer.ViewModel,
                    "AuthViewModel",
                    "setMe",
                    LogType.Info,
                    "set Me success, name: ${me.name} email: ${me.email}"
                )

            } else {
                MyLog.log(
                    StructureLayer.ViewModel,
                    "AuthViewModel",
                    "setMe",
                    LogType.Error,
                    "set Me failed, message: ${result.message}"
                )
                error(result.message!!)
            }
        }
    }

    fun login(
        sharedPrefManager: SharedPrefManager,
        email: String,
        password: String
    ) {

        viewModelScope.launch {
            startLoading()
            val result = userRepository.login(email, password)

            if (result.status == ApiStatus.SUCCESS) {
                MyLog.log(
                    StructureLayer.ViewModel,
                    "AuthViewModel",
                    "login",
                    LogType.Info,
                    "login success email: $email "
                )

                token = "Token " + result.data!!.token
                saveToken(sharedPrefManager, token)
                setMe()
            } else {
                MyLog.log(
                    StructureLayer.ViewModel,
                    "AuthViewModel",
                    "login",
                    LogType.Error,
                    "login failed, message: ${result.message}"
                )
                error(result.message!!)
            }
        }
    }

    fun register(
        name: String,
        email: String,
        iconId: Int,
        password: String
    ) {
        viewModelScope.launch {
            startLoading()
            val user = User(
                id = -1,
                name = name,
                email = email,
                iconId = iconId,
                password = password,
            )
            val result = userRepository.register(user)
            if (result.status == ApiStatus.SUCCESS) {
                MyLog.log(
                    StructureLayer.ViewModel,
                    "AuthViewModel",
                    "register",
                    LogType.Info,
                    "register success email: $email name: $name iconId: $iconId"
                )
                state.value = ApiResult.Success(_data = null, _message = EMAIL_VERIFICATION)
            } else {
                MyLog.log(
                    StructureLayer.ViewModel,
                    "AuthViewModel",
                    "register",
                    LogType.Error,
                    "register failed, message: ${result.message}"
                )
                error(result.message!!)
            }
        }
    }

    fun editProfile(name: String, picture_id: Int) {
        viewModelScope.launch {
            val editProfileInfo = userRepository.editProfile(token = token, name, picture_id)
            if (editProfileInfo.status == ApiStatus.SUCCESS) {

                me = User(id = me.id, name = name, email = me.email, iconId = picture_id)

                MyLog.log(
                    StructureLayer.ViewModel,
                    "AuthViewModel",
                    "editProfile",
                    LogType.Info,
                    "editProfile success name: $name picture_id: $picture_id"
                )
            }else{
                MyLog.log(
                    StructureLayer.ViewModel,
                    "AuthViewModel",
                    "editProfile",
                    LogType.Error,
                    "editProfile failed, message: ${editProfileInfo.message}"
                )
            }
            editUserState.value = editProfileInfo
        }
    }

    private fun saveToken(sharedPrefManager: SharedPrefManager, token: String) {
        viewModelScope.launch {
            sharedPrefManager.putString(SharedPrefManager.KEY_TOKEN, token)

            MyLog.log(
                StructureLayer.ViewModel,
                "AuthViewModel",
                "saveToken",
                LogType.Info,
                "saveToken success"
            )
        }
    }

    fun logout(sharedPrefManager: SharedPrefManager) {
        empty()
        sharedPrefManager.putString(SharedPrefManager.KEY_TOKEN, null)
        MyLog.log(
            StructureLayer.ViewModel,
            "AuthViewModel",
            "logout",
            LogType.Info,
            "logout success"
        )
    }

    fun editFinish() {
        editUserState.value = ApiResult.Empty()
        MyLog.log(
            StructureLayer.ViewModel,
            "AuthViewModel",
            "editFinish",
            LogType.Info,
            "editFinish"
        )
    }

}