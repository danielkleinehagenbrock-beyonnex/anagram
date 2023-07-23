package io.beyonnex.anagram.router

import io.beyonnex.anagram.handler.AnagramHandlerFunctions
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
