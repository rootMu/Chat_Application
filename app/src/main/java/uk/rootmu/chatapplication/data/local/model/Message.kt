package uk.rootmu.chatapplication.data.local.model

data class Message(
    val content: String,
    val sender: String,
    val recipient: String,
    val timestamp: Long
)

val Message.MessageToEntity get() = MessageEntity(
    this.content,
    this.sender,
    this.recipient,
    this.timestamp
)