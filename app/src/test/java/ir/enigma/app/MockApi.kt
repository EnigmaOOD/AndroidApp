package ir.enigma.app

import ir.enigma.app.model.*
import ir.enigma.app.network.Api
import ir.enigma.app.repostitory.CreatePurchaseRequest
import retrofit2.Response

class MockApi : Api {
    override suspend fun login(email: String, password: String): Response<Token> {
        return Response.success(Token("token"))
    }

    override suspend fun register(user: User): Response<User> {
        return Response.success(user)
    }

    override suspend fun userInfo(token: String): Response<UserInfo> {
        return Response.success(UserInfo(User(1, "test", "test", 2, "test")));
    }

    override suspend fun getGroups(token: String): Response<GroupList> {
        return Response.success(GroupList(listOf(Group(1, "test", 1, "test"))))
    }

    override suspend fun getGroupPurchases(token: String, groupId: Int): Response<List<Purchase>> {
        return Response.success(
            listOf(
                Purchase(
                    title = "test",
                    "2022-02-02",
                    totalPrice = 2000.0,
                    sender = User(1, "test", "test", 2, "test"),
                    purchaseCategoryIndex = 2,
                    buyers = listOf(
                        Contribution(
                            User(1, "test", "test", 2, "test"), 2000.0
                        ),
                    ),
                    consumers = listOf(
                        Contribution(
                            User(2, "test2", "test2", 5, "test2"), 2000.0
                        ),
                    )
                )
            )
        )

    }

    override suspend fun getAGroup(token: String, groupId: Int): Response<Group> {
        return Response.success(Group(1, "test", 1, "test"))

    }

    override suspend fun getGroupMembers(token: String, groupId: Int): Response<List<Member>> {
        return Response.success(
            listOf(
                Member(
                    User(1, "test", "test", 2, "test"),
                    2000.0,

                ),
                Member(
                    User(2, "test2", "test2", 5, "test2"),
                    2000.0,
                ),
            )
        )
    }

    override suspend fun createPurchase(
        token: String,
        purchase: CreatePurchaseRequest
    ): Response<Any> {
        return Response.success(Any())
    }

    override suspend fun getGroupDebtAndCredit(
        token: String,
        groupId: Int,
        userId: Int
    ): Response<Double> {
        return Response.success(2000.0)
    }

}