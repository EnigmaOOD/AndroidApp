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
import retrofit2.http.PATCH
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

    @POST("/group/CreateGroup/")
    suspend fun createGroup(
        @Header("Authorization") token: String,
        @Body addGroupRequest: AddGroupRequest
    ): Response<Unit>


    @POST("/buy/UserGroupBuys/")
    @FormUrlEncoded
    suspend fun filterByMe(
        @Header("Authorization") token: String,
        @Field("groupID") groupId: Int
    ): Response<UserGroupBuysResponse>

    class UserGroupBuysResponse(
        val buyer_buys: List<Purchase>,
    )

    @POST("/buy/GetGroupBuys/")
    @FormUrlEncoded
    suspend fun filterBaseDecrease(
        @Header("Authorization") token: String,
        @Field("groupID") groupId: Int,
        @Field("sort") sort: String = "cost"
    ): Response<List<Purchase>>


    @PATCH("/auth/EditProfile/")
    @FormUrlEncoded
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("picture_id") picture_id: Int,
    ): Response<Any>

    @POST("/auth/DeleteUser/")
    @FormUrlEncoded
    suspend fun leaveGroup(
        @Header("Authorization") token: String,
        @Field("groupID") groupID: Int,
        @Field("userID") userID: Int,
    ): Response<Any>

    @POST("/group/AddUserGroup/")
    suspend fun addUserToGroup(
        @Header("Authorization") token: String,
        @Body addUserToGroupRequest: AddUserToGroupRequest
    ): Response<Unit>
}

data class AddUserToGroupRequest (
    val groupID: Int,
    val emails: List<String>)

data class AddGroupRequest(
    val name: String,
    val picture_id: Int,
    val currency: String,
    val emails: List<String>
)