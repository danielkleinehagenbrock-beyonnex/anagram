package io.beyonnex.anagram.router

import org.springframework.http.MediaType

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
