package ir.enigma.app.model

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("user_info")
    val user: User
)
data class User(
    @SerializedName("user_id")
    val id: Int,
    val email: String,
    val name: String,
    @SerializedName("picture_id") val iconId: Int,
    val password: String? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (other is User)
            return other.id == id;
        return false
    }

    override fun hashCode(): Int {
        return id
    }
}