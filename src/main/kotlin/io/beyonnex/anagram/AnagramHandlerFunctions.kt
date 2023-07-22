package io.beyonnex.anagram

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class AnagramHandlerFunctions {
    suspend fun checkForAnagram(serverRequest: ServerRequest): ServerResponse =
        serverRequest.awaitBody(AnagramCheckRequest::class)
            .let { request -> request.possibleAnagram isAnagramOf request.source }
            .let { AnagramCheckResponse(it) }
            .let {
                ServerResponse
                    .ok()
                    .contentType(AnagramCheckResponse.mediaType)
                    .bodyValueAndAwait(it)
            }
}
