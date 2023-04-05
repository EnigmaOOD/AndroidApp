package ir.enigma.app.data

import ir.enigma.app.model.User


val me = User(-1, "me@me.com", "احمد احمدی")

val userA = User(0, "a@a.com", "علی علوی")

val userB = User(1, "b@b.com", "محمد محمدی")

val userC = User(2, "c@c.com", "مهدی مهدوی")

val userD = User(3, "d@d.com", "محمود محمودی")

val userE = User(4, "e@e.com", "حسین حسینی")

val fakeUsers = listOf(
    me, userA, userB, userC, userD, userE
)