package uk.rootmu.chatapplication.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uk.rootmu.chatapplication.data.local.model.Message
import uk.rootmu.chatapplication.data.repository.ChatRepository

class ChatViewModel(
    private val repository: ChatRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    // MutableLiveData for the message text
    val content = MutableLiveData<String>()
    val allMessages =
        repository.getAllMessages().stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun sendMessage(newContent: String? = content.value) {
        val messageContent = newContent ?: return

        val newMessage = Message(messageContent, "John", "Bob")
        viewModelScope.launch {
            withContext(defaultDispatcher) {
                repository.insertMessage(newMessage)
            }
            content.postValue("")
        }
    }
}