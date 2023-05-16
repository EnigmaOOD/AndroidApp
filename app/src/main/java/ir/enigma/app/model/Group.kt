package ir.enigma.app.model

data class Group(
    val id: Int,
    val name: String,
    val categoryId: Int = 0,
    val currency: String,
) {

    var members: List<Member>? = null

    constructor(
        id: Int,
        name: String,
        categoryId: Int = 0,
        currency: String,
        members: List<Member>
    ) : this(
        id,
        name,
        categoryId, currency
    ){
        this.members = members
    }

}
