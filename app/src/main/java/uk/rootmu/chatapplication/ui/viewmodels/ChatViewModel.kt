package uk.rootmu.chatapplication.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import uk.rootmu.chatapplication.data.local.chatResponses
import uk.rootmu.chatapplication.data.local.model.Message
import uk.rootmu.chatapplication.data.local.model.User.Companion.RECIPIENT
import uk.rootmu.chatapplication.data.local.model.User.Companion.SENDER
import uk.rootmu.chatapplication.data.repository.ChatRepository

class ChatViewModel(
    private val repository: ChatRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private var postJob: Job? = null

    init {
        postRandomMessages()
    }

    private fun postRandomMessages() {
        postJob?.cancel()

        postJob = viewModelScope.launch {
            withTimeout((3001L..20_000).random()) {
                repeat((1..5).random()) {
                    delay((1000L..3000L).random())
                    sendRandomMessage()
                }
            }
        }
    }

    val content = MutableLiveData<String>()
    val allMessages =
        repository.getAllMessages().stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun sendMessage(newContent: String? = content.value) {
        val messageContent = newContent ?: return

        if (messageContent.isBlank())
            return

        val newMessage = Message(messageContent, SENDER.name, RECIPIENT.name)
        viewModelScope.launch {
            withContext(defaultDispatcher) {
                repository.insertMessage(newMessage)
            }
            content.postValue("")
            postRandomMessages()
        }
    }

    private fun sendRandomMessage() {
        val randomMessage = chatResponses.random()
        val newMessage = Message(randomMessage, RECIPIENT.name, SENDER.name)
        viewModelScope.launch {
            withContext(defaultDispatcher) {
                repository.insertMessage(newMessage)
            }
        }
    }
}