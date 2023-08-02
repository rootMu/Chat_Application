package uk.rootmu.chatapplication.ui

import uk.rootmu.chatapplication.data.local.model.User

interface RecipientListener {
    fun onRecipientChanged(recipient: User)
}