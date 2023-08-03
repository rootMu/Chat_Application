package uk.rootmu.chatapplication

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import uk.rootmu.chatapplication.data.local.model.Message
import uk.rootmu.chatapplication.data.repository.ChatRepository
import uk.rootmu.chatapplication.ui.viewmodels.ChatViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Mock
    private lateinit var mockRepository: ChatRepository
    private lateinit var testSubject: ChatViewModel

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        testSubject = ChatViewModel(mockRepository, testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @org.junit.jupiter.api.Test
    fun `test that message is correctly added to database`() = testScope.runTest {
        val content = "Hello World!"
        val sender = "John"
        val recipient = "Bob"
        val newMessage = Message(content, sender, recipient)

        // Mock the behavior of the repository
        `when`(mockRepository.insertMessage(newMessage)).thenAnswer { invocation ->
            val message = invocation.arguments[0] as Message

            assert(message.content == content)
            assert(message.sender == sender)
            assert(message.recipient == recipient)

            // Return Unit to simulate a successful insertion
        }

        // When
        testSubject.sendMessage(content)

        // Then
        verify(mockRepository).insertMessage(newMessage)
    }

    @org.junit.jupiter.api.Test
    fun `test that messages are retrieved correctly from the database`() = testScope.runTest {

        // Given
        val messagesFlow: Flow<List<Message>> = flowOf(emptyList())


        // Mock the behavior of the repository
        `when`(mockRepository.getAllMessages()).thenReturn(messagesFlow)

        // When
        val resultFlow = testSubject.allMessages

        // Then
        val messages = mutableListOf<Message>()
        val job = launch {
            try {
                resultFlow.collect { messagesList ->
                    messagesList?.let{
                        messages.addAll(it)
                    }

                }
            } catch (e: Throwable) {
                // Handle exceptions here if needed
            }
        }

        testScope.advanceUntilIdle()
        job.cancel()

        assert(messages.isEmpty())
    }

}