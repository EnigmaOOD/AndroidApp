package ir.enigma.app.data

import ir.enigma.app.model.User


val me = User(-1, "me@me.com", "احمد احمدی", iconId = 16)

val userA = User(0, "a@a.com", "علی علوی", iconId = 3)

val userB = User(1, "b@b.com", "محمد محمدی", iconId = 11)

val userC = User(2, "c@c.com", "مهدی مهدوی", iconId = 15)

val userD = User(3, "d@d.com", "محمود محمودی", iconId = 21)

val userE = User(4, "e@e.com", "حسین حسینی", iconId = 19)

val fakeUsers = listOf(
    me, userA, userB, userC, userD, userE
)