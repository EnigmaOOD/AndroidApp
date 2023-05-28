package ir.enigma.app.repostitory
import ir.enigma.app.data.ApiResult
import ir.enigma.app.model.Token
import ir.enigma.app.model.User
import ir.enigma.app.model.UserInfo
import ir.enigma.app.network.Api
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: Api) {

    companion object {

        const val UN_AUTHORIZE_ERROR: String =  "برای ادامه باید دوباره وارد شوید"
        const val EMAIL_EXIST = "کاربری با این ایمیل وجود دارد"
    }

    suspend fun login(email: String, password: String): ApiResult<Token> {

        return handleException({
            api.login(email, password)
        }) {
            if (it / 100 == 4) {
                "ایمیل یا رمز عبور اشتباه است"
            } else
                null
        }
    }

    suspend fun register(user: User): ApiResult<User> {
        return handleException({
            api.register(user)
        }) {

            if (it == 400)
                EMAIL_EXIST
            else
                null
        }
    }

    suspend fun getUserInfo(token: String): ApiResult<UserInfo> {
        return handleException({
            api.userInfo(token)
        }) {
            if (it == 401)
                UN_AUTHORIZE_ERROR
            else
                null
        }
    }

    suspend fun editProfile(token: String, name: String, picture_id: Int): ApiResult<Any> {
        return handleException({
            api.editProfile(token, name, picture_id)
        }) {
            if (it == 401)
                "برای ادامه باید دوباره وارد شوید"
            else
                null
        }
    }
}