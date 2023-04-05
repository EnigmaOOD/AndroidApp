package ir.enigma.app.model

data class User(val id: Int, val email: String, val name: String){
    override fun equals(other: Any?): Boolean {
        if (other is User)
            return other.id == id;
        return false
    }

    override fun hashCode(): Int {
        return id
    }
}