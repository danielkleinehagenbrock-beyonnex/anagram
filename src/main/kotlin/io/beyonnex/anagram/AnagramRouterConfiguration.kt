package io.beyonnex.anagram

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class AnagramRouterConfiguration(
    private val handlerFunctions: AnagramHandlerFunctions
) {

    @Bean
    fun anagramRouter(): RouterFunction<ServerResponse> = coRouter {
        POST(anagramCheckUrl).nest {
            contentType(AnagramCheckRequest.mediaType, handlerFunctions::checkForAnagram)
            contentType(MediaType.ALL) { ServerResponse.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).buildAndAwait() }
        }
    }

    companion object {
        const val anagramCheckUrl: String = "/anagram-check"
    }
}

data class AnagramCheckRequest(
    val source: String,
    val possibleAnagram: String
) {
    companion object {
        val mediaType: MediaType = MediaType("application", "vnd.beyonnex.anagram-check.request.v1+json")
    }
}

data class AnagramCheckResponse(
    val isAnagram: Boolean
) {
    companion object {
        val mediaType: MediaType = MediaType("application", "vnd.beyonnex.anagram-check.response.v1+json")
    }
}
