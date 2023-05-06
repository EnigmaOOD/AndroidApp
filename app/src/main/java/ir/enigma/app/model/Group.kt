package ir.enigma.app.model

data class Group(
    val id: Int,
    val name: String,
    val categoryId: Int = 0,
    val currency: String,
) {

    var members: List<Member>? = null


}
