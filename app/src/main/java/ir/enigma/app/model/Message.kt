package ir.enigma.app.model


data class Message(val textId: Int, val type: MessageType)

enum class MessageType {
    Success, Error, Warning, Info
}