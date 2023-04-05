package ir.enigma.app.model

data class User(val id: Int, val email: String, val name: String, val iconId: Int = 0) {

    override fun equals(other: Any?): Boolean {
        if (other is User)
            return other.id == id;
        return false
    }

    override fun hashCode(): Int {
        return id
    }
}