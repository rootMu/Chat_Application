package uk.rootmu.chatapplication.data.local.model

data class User(
    val name: String
) {
    companion object {
        val SENDER = User("John")
        val RECIPIENT = User("BOB")
    }
}