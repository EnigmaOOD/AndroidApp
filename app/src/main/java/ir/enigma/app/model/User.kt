package ir.enigma.app.model

import com.google.gson.annotations.SerializedName

/*
{
user_id: 2
}
 */
data class User(
    @SerializedName("user_id") val id: Int,
    val email: String,
    @SerializedName("username") val name: String,
    @SerializedName("picture_id") val iconId: Int = 0,
    val password: String? = null,
) {
    var token: String? = null

    override fun equals(other: Any?): Boolean {
        if (other is User)
            return other.id == id;
        return false
    }

    override fun hashCode(): Int {
        return id
    }
}