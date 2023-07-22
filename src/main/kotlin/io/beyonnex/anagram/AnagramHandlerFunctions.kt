package io.beyonnex.anagram

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class AnagramHandlerFunctions {
    suspend fun checkForAnagram(serverRequest: ServerRequest): ServerResponse =
        checkForAnagram(serverRequest) { request -> request.possibleAnagram isAnagramOf request.source }

    suspend fun checkForAnagramVerifiedBySorting(serverRequest: ServerRequest): ServerResponse =
        checkForAnagram(serverRequest) { request -> request.possibleAnagram isAnagramOfVerifiedBySorting request.source }

    private suspend fun checkForAnagram(
        serverRequest: ServerRequest,
        checkFunction: (AnagramCheckRequest) -> Boolean
    ): ServerResponse =
        serverRequest.awaitBody(AnagramCheckRequest::class)
            .let { request -> checkFunction(request) }
            .let { AnagramCheckResponse(it) }
            .let {
                ServerResponse
                    .ok()
                    .contentType(AnagramCheckResponse.mediaType)
                    .bodyValueAndAwait(it)
            }
}
