package uk.rootmu.chatapplication

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineExceptionHandler
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.createTestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runBlockingTestOnTestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import uk.rootmu.chatapplication.data.local.model.Message
import uk.rootmu.chatapplication.data.repository.ChatRepository
import uk.rootmu.chatapplication.ui.viewmodels.ChatViewModel

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Mock
    private lateinit var mockRepository : ChatRepository
    private lateinit var testSubject: ChatViewModel


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        testSubject = ChatViewModel(mockRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test that message is correctly added to database`()  = testScope.runTest {
        val newMessage = Message("Hello World!", "John", "Bob", 0)
        // Mock the behavior of the repository
        `when`(mockRepository.insertMessage(newMessage)).thenReturn(Unit)

        // When
        testSubject.insertMessage(newMessage)

        // Then
        verify(mockRepository).insertMessage(newMessage)
    }

    @Test
    fun `test that messages are retrieved correctly from the database`() =  testScope.runTest {

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
                    messages.addAll(messagesList)
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