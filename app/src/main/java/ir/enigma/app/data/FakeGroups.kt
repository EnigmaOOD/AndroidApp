package ir.enigma.app.data

import ir.enigma.app.model.Group


val groupA = Group(0, "گروه اول", categoryId = 0, listOf(userD, userE, userB))

val groupB = Group(1, "گروه دوم", categoryId = 1, listOf(userD, userE, userB))

val groupC = Group(2, "گروه سوم", categoryId = 2, listOf(userD, userE, userB))

val groupD = Group(3, "گروه چهارم", categoryId = 3, listOf(userD, userE, userB))

val fakeGroups = listOf(
    groupA, groupB, groupC, groupD
)