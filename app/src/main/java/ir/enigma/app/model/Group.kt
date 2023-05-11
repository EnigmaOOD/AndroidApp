package ir.enigma.app.model

data class Group(
    val id: Int,
    val name: String,
    val categoryId: Int = 0,
    val currency: String,
) {


    lateinit var members: List<Member>


}
