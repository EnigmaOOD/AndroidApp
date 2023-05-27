package ir.enigma.app.repostitory

import android.util.Log
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.Token
import ir.enigma.app.model.User
import ir.enigma.app.model.UserInfo
import ir.enigma.app.network.Api
import ir.enigma.app.util.LogType
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.StructureLayer
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: Api) {

    companion object {

        const val UN_AUTHORIZE_ERROR: String = "برای ادامه باید دوباره وارد شوید"
        const val EMAIL_EXIST = "کاربری با این ایمیل وجود دارد"
    }

    suspend fun login(email: String, password: String): ApiResult<Token> {

        val result = handleException({
            api.login(email, password)
        }) {
            if (it / 100 == 4) {
                MyLog.log(
                    StructureLayer.Repository,
                    "UserRepository", "login", LogType.Error,
                    "login with email: $email Failed because email or password is wrong (errorCode: $it)"
                )

                "ایمیل یا رمز عبور اشتباه است"
            } else {
                MyLog.log(
                    StructureLayer.Repository,
                    "UserRepository", "login", LogType.Error,
                    "login with email: $email Failed Not handled errorCode: $it"
                )

                null
            }
        }

        if (result.status == ApiStatus.SUCCESS) {
            MyLog.log(
                StructureLayer.Repository,
                "UserRepository", "login", LogType.Info,
                "login with email: $email Success"
            )
        }

        return result
    }

    suspend fun register(user: User): ApiResult<User> {
        return handleException({
            api.register(user)
        }) {

            if (it == 400) {
                MyLog.log(
                    StructureLayer.Repository,
                    "UserRepository", "register", LogType.Error,
                    "register with email: ${user.email} Failed because email exist"
                )
                EMAIL_EXIST
            } else {
                MyLog.log(
                    StructureLayer.Repository,
                    "UserRepository", "register", LogType.Error,
                    "register with email: ${user.email} Failed because Not handled errorCode: $it"
                )
                null
            }
        }
    }

    suspend fun getUserInfo(token: String): ApiResult<UserInfo> {

        val result = handleException({
            api.userInfo(token)
        }) {
            if (it == 401) {
                MyLog.log(
                    StructureLayer.Repository,
                    "UserRepository", "getUserInfo", LogType.Error,
                    "getUserInfo Failed because token is invalid"
                )
                UN_AUTHORIZE_ERROR
            } else {
                MyLog.log(
                    StructureLayer.Repository,
                    "UserRepository", "getUserInfo", LogType.Error,
                    "getUserInfo Failed because Not handled errorCode: $it"
                )
                null
            }
        }

        if (result.status == ApiStatus.SUCCESS) {
            MyLog.log(
                StructureLayer.Repository,
                "UserRepository", "getUserInfo", LogType.Info,
                "getUserInfo  Success"
            )
        }

        return result
    }

    suspend fun editProfile(token: String, name: String, picture_id: Int): ApiResult<Any> {
        val result = handleException({
            api.editProfile(token, name, picture_id)
        }) {
            MyLog.log(
                StructureLayer.Repository,
                "UserRepository", "editProfile", LogType.Error,
                "editProfile Failed because Not handled errorCode: $it"
            )
            null
        }

        if (result.status == ApiStatus.SUCCESS) {
            MyLog.log(
                StructureLayer.Repository,
                "UserRepository", "editProfile", LogType.Info,
                "editProfile  Success"
            )
        }

        return result
    }
}