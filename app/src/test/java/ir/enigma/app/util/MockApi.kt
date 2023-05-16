package ir.enigma.app.util
//
//import ir.enigma.app.model.*
//import ir.enigma.app.network.AddGroupRequest
//import ir.enigma.app.network.AddUserToGroupRequest
//import ir.enigma.app.network.Api
//import ir.enigma.app.repostitory.CreatePurchaseRequest
//import retrofit2.Response
//
//class MockApi : Api {
//    override suspend fun login(email: String, password: String): Response<Token> {
//        return Response.success(Token("token"))
//    }
//
//    override suspend fun register(user: User): Response<User> {
//        return Response.success(user)
//    }
//
//    override suspend fun userInfo(token: String): Response<UserInfo> {
//        return Response.success(UserInfo(User(1, "test", "test", 2, "test")));
//    }
//
//    override suspend fun getGroups(token: String): Response<GroupList> {
//        return Response.success(GroupList(listOf(Group(1, "test", 1, "test"))))
//    }
//
//    override suspend fun getGroupPurchases(token: String, groupId: Int): Response<List<Purchase>> {
//        return Response.success(
//            listOf(
//                fakePurchase1, fakePurchase2
//            )
//        )
//
//    }
//
//    override suspend fun getAGroup(token: String, groupId: Int): Response<Group> {
//        return Response.success(Group(1, "test", 1, "test"))
//
//    }
//
//    override suspend fun getGroupMembers(token: String, groupId: Int): Response<List<Member>> {
//        return Response.success(
//            listOf(
//                Member(
//                    User(1, "test", "test", 2, "test"),
//                    2000.0,
//
//                    ),
//                Member(
//                    User(2, "test2", "test2", 5, "test2"),
//                    2000.0,
//                ),
//            )
//        )
//    }
//
//    override suspend fun createPurchase(
//        token: String,
//        purchase: CreatePurchaseRequest
//    ): Response<Any> {
//        return Response.success(Any())
//    }
//
//    override suspend fun getGroupDebtAndCredit(
//        token: String,
//        groupId: Int,
//        userId: Int
//    ): Response<Double> {
//        return Response.success(2000.0)
//    }
//
//    override suspend fun createGroup(
//        token: String,
//        addGroupRequest: AddGroupRequest
//    ): Response<Unit> {
//        return Response.success(Unit)
//    }
//
//    override suspend fun filterByMe(
//        token: String,
//        groupId: Int
//    ): Response<Api.UserGroupBuysResponse> {
//        return Response.success(Api.UserGroupBuysResponse(listOf(fakePurchase1, fakePurchase2)))
//    }
//
//    override suspend fun filterBaseDecrease(
//        token: String,
//        groupId: Int,
//        sort: String
//    ): Response<List<Purchase>> {
//        return Response.success(listOf(fakePurchase1, fakePurchase2))
//    }
//
//    override suspend fun editProfile(token: String, name: String, picture_id: Int): Response<Any> {
//        return Response.success(Unit)
//    }
//
//    override suspend fun leaveGroup(token: String, groupID: Int, userID: Int): Response<Any> {
//        return Response.success(Unit)
//    }
//
//    override suspend fun addUserToGroup(
//        token: String,
//        addUserToGroupRequest: AddUserToGroupRequest
//    ): Response<Unit> {
//        return Response.success(Unit)
//    }
//
//
//}
//
