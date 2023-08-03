package uk.rootmu.chatapplication.data.remote

import uk.rootmu.chatapplication.data.remote.model.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import uk.rootmu.chatapplication.data.remote.model.GeneratedAnswer

interface ApiService {
    @POST("chat/completions")
    suspend fun getPrompt(@Body body: RequestBody) : GeneratedAnswer

    @POST("completions")
    suspend fun getLegacyPrompt(@Body body: RequestBody) : GeneratedAnswer
}

enum class Models(val family: String) {
    GPT4("gpt-4"),
    GPT35("gpt-3.5-turbo"),
    DAVINCI3("text-davinci-003"),
    DAVINCI2("text-davinci-002"),
    DAVINCI("davinci"),
    CURIE("curie"),
    BABBAGE("babbage"),
    ADA("ada");

}