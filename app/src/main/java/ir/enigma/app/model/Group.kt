package ir.enigma.app.model

import kotlin.properties.Delegates

data class Group(
    val id: Int,
    val name: String,
    val categoryId: Int = 0,
    val currency: String,
) {
    var members: List<Member>? = null

    class Builder {
        var id: Int = 0
        var name: String by Delegates.notNull()
        var categoryId: Int = 0
        var  currency : String by Delegates.notNull()
        var members: List<Member>? = emptyList()

        fun id(id: Int) = apply { this.id = id }
        fun name(name: String) = apply { this.name = name }
        fun categoryId(categoryId: Int) = apply { this.categoryId = categoryId }
        fun currency(currency: String) = apply { this.currency = currency }
        fun members(members: List<Member>) = apply { this.members = members }

        fun build() = Group(id, name, categoryId, currency)
    }

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
    ) {
        this.members = members
    }

}
