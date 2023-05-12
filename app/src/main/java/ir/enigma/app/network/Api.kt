package ir.enigma.app.network

import ir.enigma.app.model.*
import ir.enigma.app.repostitory.CreatePurchaseRequest
import kotlinx.coroutines.flow.Flow
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


    @POST("/auth/register/")
    suspend fun register(
        @Body user: User
    ): Response<User>

    @POST("/auth/UserInfo/")
    suspend fun userInfo(
        @Header("Authorization") token: String
    ): Response<UserInfo>


    @POST("/group/ShowGroups/")
    suspend fun getGroups(
        @Header("Authorization") token: String,
    ): Response<GroupList>


    @POST("/buy/GetGroupBuys/")
    @FormUrlEncoded
    suspend fun getGroupPurchases(
        @Header("Authorization") token: String,
        @Field("groupID") groupId: Int
    ): Response<List<Purchase>>

    @POST("/group/GroupInfo/")
    @FormUrlEncoded
    suspend fun getAGroup(
        @Header("Authorization") token: String,
        @Field("groupID") groupId: Int
    ): Response<Group>

    @POST("/group/ShowMembers/")
    @FormUrlEncoded
    suspend fun getGroupMembers(
        @Header("Authorization") token: String,
        @Field("groupID") groupId: Int
    ): Response<List<Member>>


    @POST("/buy/CreateBuyView/")
    suspend fun createPurchase(
        @Header("Authorization") token: String,
        @Body purchase: CreatePurchaseRequest
    ): Response<Any>

    @POST("/group/AmountofDebtandCredit/")
    @FormUrlEncoded
    suspend fun getGroupDebtAndCredit(
        @Header("Authorization") token: String,
        @Field ("groupID") groupId: Int,
        @Field ("userID") userId: Int
    ): Response<Double>
}
