# Chat_Application

# Overview
This is a Chat Application built in Kotlin, following the MVVM (Model-View-ViewModel) architectural pattern. 
It utilizes Koin for dependency injection and Room for local database storage. 
Databinding and ViewBinding are used to enhance the UI and make data binding more efficient. 
The app allows a user to send and receive messages in real-time.

# Features
MVVM Architecture: The app follows the MVVM design pattern, separating the UI from business logic and data handling.
Koin Dependency Injection: Koin is used for dependency injection, providing a lightweight and efficient way to manage app components.
Room Database: The app uses Room, a SQLite object-relational mapping library, to store and retrieve messages locally.
Databinding and ViewBinding: Databinding and ViewBinding are used to bind UI components to data and eliminate boilerplate code.
Two-Way Databinding: Two-way databinding is implemented to enable automatic updates of UI components when data changes and vice versa.
View Extensions and BindingAdapters: Custom view extensions and BindingAdapters are used to simplify UI interactions and enhance functionality.

# Testing
## Test 1: "Test that message is correctly added to the database"

Verifies that a new message is correctly inserted into the database when the sendMessage function is called with valid content, sender, and recipient values.
Uses a mock repository to simulate the insertion process and checks whether the correct message content, sender, and recipient are passed to the repository.
Uses Mockito to mock the ChatRepository and verifies that the insertMessage function is called with the correct message object.

## Test 2: "Test that messages are retrieved correctly from the database"

Ensures that messages are retrieved from the database as expected.
Uses a mock repository to return an empty flow of messages.
Collects the flow of messages from the viewmodel and verifies that the messages list is empty after the collection.
Uses kotlinx.coroutines.test to run the tests with the testDispatcher and testScope, ensuring that coroutines are handled correctly during testing.
The advanceUntilIdle function is used to advance the testDispatcher until all coroutines are idle, ensuring that all suspended coroutines are completed.
The tests enhance the reliability and maintainability of the Chat Application by catching potential issues early and allowing for quick feedback during development.
