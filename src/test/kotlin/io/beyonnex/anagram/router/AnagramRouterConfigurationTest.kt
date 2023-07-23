package io.beyonnex.anagram.router

import com.ninjasquad.springmockk.SpykBean
import io.beyonnex.anagram.handler.AnagramHandlerFunctions
import io.mockk.coEvery
import io.mockk.coVerify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@AutoConfigureWebTestClient
@SpringBootTest
class AnagramRouterConfigurationTest(
    @Autowired private val client: WebTestClient
) {

    @SpykBean
    private lateinit var handlerFunctions: AnagramHandlerFunctions

    @Test
    fun `Verify anagram check endpoint successfully verifies anagrams with counting chars`() {
        // given/when
        val response = client.exchangeAnagramCheck(
            request = AnagramCheckRequest(
                source = "abc",
                possibleAnagram = "cba"
            ),
            contentType = AnagramCheckRequest.mediaTypeCount
        )
        val expected = AnagramCheckResponse(isAnagram = true)

        // then
        response
            .expectStatus().is2xxSuccessful
            .expectHeader().contentType(AnagramCheckResponse.mediaType)
            .expectBody(AnagramCheckResponse::class.java).isEqualTo(expected)
        coVerify(atLeast = 1) { handlerFunctions.checkForAnagram(any()) }
    }

    @Test
    fun `Verify anagram check endpoint identifies non anagrams with counting chars`() {
        // given/when
        val response = client.exchangeAnagramCheck(
            request = AnagramCheckRequest(
                source = "abc",
                possibleAnagram = "def"
            ),
            contentType = AnagramCheckRequest.mediaTypeCount
        )
        val expected = AnagramCheckResponse(isAnagram = false)

        // then
        response
            .expectStatus().is2xxSuccessful
            .expectHeader().contentType(AnagramCheckResponse.mediaType)
            .expectBody(AnagramCheckResponse::class.java).isEqualTo(expected)
        coVerify(atLeast = 1) { handlerFunctions.checkForAnagram(any()) }
    }

    @Test
    fun `Verify anagram check endpoint successfully verifies anagrams with sorting chars`() {
        // given/when
        val response = client.exchangeAnagramCheck(
            request = AnagramCheckRequest(
                source = "abc",
                possibleAnagram = "cba"
            ),
            contentType = AnagramCheckRequest.mediaTypeSort
        )
        val expected = AnagramCheckResponse(isAnagram = true)

        // then
        response
            .expectStatus().is2xxSuccessful
            .expectHeader().contentType(AnagramCheckResponse.mediaType)
            .expectBody(AnagramCheckResponse::class.java).isEqualTo(expected)
        coVerify(atLeast = 1) { handlerFunctions.checkForAnagramVerifiedBySorting(any()) }
    }

    @Test
    fun `Verify anagram check endpoint identifies non anagrams with sorting chars`() {
        // given/when
        val response = client.exchangeAnagramCheck(
            request = AnagramCheckRequest(
                source = "abc",
                possibleAnagram = "def"
            ),
            contentType = AnagramCheckRequest.mediaTypeSort
        )
        val expected = AnagramCheckResponse(isAnagram = false)

        // then
        response
            .expectStatus().is2xxSuccessful
            .expectHeader().contentType(AnagramCheckResponse.mediaType)
            .expectBody(AnagramCheckResponse::class.java).isEqualTo(expected)
        coVerify(atLeast = 1) { handlerFunctions.checkForAnagramVerifiedBySorting(any()) }
    }

    @Test
    fun `Verify anagram returns 400 - Bad Request on falsely json`() {
        // given/when
        val response = client.post()
            .uri(AnagramRouterConfiguration.anagramCheckUrl)
            .contentType(AnagramCheckRequest.mediaTypeCount)
            .bodyValue(
                """{"source":"abc","possible anagram":"def"}""".trimIndent()
            )
            .exchange()

        // then
        response.expectStatus().isBadRequest
    }

    @Test
    fun `Verify anagram returns 500 - Internal Server Error on internal error`() {
        // given/when
        coEvery { handlerFunctions.checkForAnagram(any()) } throws RuntimeException()
        val response = client.exchangeAnagramCheck(
            request = AnagramCheckRequest(
                source = "abc",
                possibleAnagram = "def"
            ),
            contentType = AnagramCheckRequest.mediaTypeCount
        )

        // then
        response.expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
        coVerify(atLeast = 1) { handlerFunctions.checkForAnagram(any()) }
    }

    @Test
    fun `Verify anagram check endpoint returns 415 - Unsupported Media Type on missing media type`() {
        // given/when
        val response = client.exchangeAnagramCheck(contentType = null)

        // then
        response.expectStatus().isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    }

    @Test
    fun `Verify anagram check endpoint returns 415 - Unsupported Media Type on incorrect media type`() {
        // given/when
        val response = client.exchangeAnagramCheck(contentType = MediaType.APPLICATION_XML)

        // then
        response.expectStatus().isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    }

    private fun WebTestClient.exchangeAnagramCheck(
        contentType: MediaType?,
        request: AnagramCheckRequest? = null
    ) = this.post()
        .uri(AnagramRouterConfiguration.anagramCheckUrl)
        .apply { contentType?.let { this.contentType(it) } }
        .apply { request?.let { this.bodyValue(it) } }
        .exchange()
}
