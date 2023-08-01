package uk.rootmu.chatapplication.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uk.rootmu.chatapplication.data.local.model.Message
import uk.rootmu.chatapplication.data.repository.ChatRepository

class ChatViewModel(private val repository: ChatRepository, private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) : ViewModel() {

    val allMessages = repository.getAllMessages()

    fun insertMessage(message: Message) {
        viewModelScope.launch {
            withContext(defaultDispatcher) {
                repository.insertMessage(message)
            }
        }
    }
}