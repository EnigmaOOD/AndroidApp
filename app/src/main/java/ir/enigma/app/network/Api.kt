package ir.enigma.app.network

import ir.enigma.app.model.Group
import ir.enigma.app.model.Token
import ir.enigma.app.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {


    @POST("/auth/token/")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") email: String,
        @Field("password") password: String
    ): Response<Token>

    //register
    @POST("/auth/register/")
    suspend fun register(
        @Body user: User
    ): Response<User>
}