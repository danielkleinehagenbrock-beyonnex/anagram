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
            contentType(AnagramCheckRequest.mediaTypeCount, handlerFunctions::checkForAnagram)
            contentType(AnagramCheckRequest.mediaTypeSort, handlerFunctions::checkForAnagramVerifiedBySorting)
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
        val mediaTypeCount: MediaType = MediaType("application", "vnd.beyonnex.anagram-check.request.count.v1+json")
        val mediaTypeSort: MediaType = MediaType("application", "vnd.beyonnex.anagram-check.request.sort.v1+json")
    }
}

data class AnagramCheckResponse(
    val isAnagram: Boolean
) {
    companion object {
        val mediaType: MediaType = MediaType("application", "vnd.beyonnex.anagram-check.response.v1+json")
    }
}
