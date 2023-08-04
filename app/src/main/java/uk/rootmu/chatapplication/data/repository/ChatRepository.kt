package uk.rootmu.chatapplication.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import uk.rootmu.chatapplication.data.remote.model.RequestBody
import uk.rootmu.chatapplication.data.local.dao.MessageDao
import uk.rootmu.chatapplication.data.remote.model.GeneratedAnswer
import uk.rootmu.chatapplication.data.local.model.Message
import uk.rootmu.chatapplication.data.remote.ApiService
import uk.rootmu.chatapplication.data.remote.Models

class ChatRepository(
    private val messageDao: MessageDao,
    private val apiService: ApiService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun insertMessage(message: Message) {
        withContext(defaultDispatcher) {
            messageDao.insertMessage(message.copy(timeStamp = System.currentTimeMillis()))
        }
    }

    fun getAllMessages(): Flow<List<Message>> {
        return messageDao.getAllMessages()
    }

    suspend fun getPrompt(message: RequestBody): GeneratedAnswer? {
        return try {
            when (message.model) {
                Models.GPT4.family,
                Models.GPT35.family -> apiService.getPrompt(message)

                else -> apiService.getLegacyPrompt(message)
            }
        }catch (e: Exception) {
            Log.d("EXCEPTION", "$e")
            null
        }
    }
}