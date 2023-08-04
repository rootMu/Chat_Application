package uk.rootmu.chatapplication.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
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
import uk.rootmu.chatapplication.data.remote.model.Message as RequestMessage
import uk.rootmu.chatapplication.data.local.model.Message
import uk.rootmu.chatapplication.data.local.model.User.Companion.RECIPIENT
import uk.rootmu.chatapplication.data.local.model.User.Companion.SENDER
import uk.rootmu.chatapplication.data.remote.Models
import uk.rootmu.chatapplication.data.remote.model.GeneratedAnswer
import uk.rootmu.chatapplication.data.remote.model.RequestBody
import uk.rootmu.chatapplication.data.remote.model.Role
import uk.rootmu.chatapplication.data.repository.ChatRepository

class ChatViewModel(
    private val repository: ChatRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private var postJob: Job? = null
    val content = MutableLiveData<String>()
    val allMessages =
        repository.getAllMessages().stateIn(viewModelScope, SharingStarted.Lazily, null)
    private val response = MutableLiveData<GeneratedAnswer>()
    private val messageResponse = response.map {
        it.choices.forEach {
            sendResponseMessage(it.text)
        }
    }

    private fun postRandomMessages() {
        postJob?.cancel()

        postJob = viewModelScope.launch {
            withTimeout((3001L..20_000).random()) {
                repeat((1..5).random()) {
                    delay((1000L..3000L).random())
                    sendResponseMessage()
                }
            }
        }
    }

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

            respond(messageContent)
            //sendResponseMessage()
        }
    }

    private fun respond(content: String) {
        viewModelScope.launch {
            withContext(defaultDispatcher) {
                response.postValue(
                    repository.getPrompt(
                        RequestBody(Models.DAVINCI3.family, createMessages(content))
                    )
                )
            }
        }
    }

    private fun createMessages(content: String): List<RequestMessage> {
        val systemMessage = RequestMessage(role = Role.SYSTEM.role, "You are about to receive a message that is pretending to be part of a chat messaging system, you will need to response with a suitable friendly response as if you were chatting in whatsapp")
        val userMessage = RequestMessage(content = content)
        return listOf(systemMessage, userMessage)
    }

    private fun sendResponseMessage(message: String = chatResponses.random()) {
        val newMessage = Message(message, RECIPIENT.name, SENDER.name)
        viewModelScope.launch {
            withContext(defaultDispatcher) {
                repository.insertMessage(newMessage)
            }
        }
    }
}