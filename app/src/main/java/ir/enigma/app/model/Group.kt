package ir.enigma.app.model

data class Group(
    val id: Int,
    val name: String,
    val categoryId: Int = 0,
    val users: List<User>?
)
