package ir.enigma.app.data

import ir.enigma.app.model.User

class UserRepository {
    companion object{
        fun getMe(): User {
            return me;
        }
        fun isMe(user: User):Boolean{
            return user == me;
        }
    }
}