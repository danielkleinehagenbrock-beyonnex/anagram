package io.beyonnex.anagram

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test

class AnagramFunctionsTest {

    @Test
    fun `Verify String#isAnagramOf classifies non anagrams`() {
        // given
        val source = "abc"
        val potentialAnagram = "def"

        // when
        val result = potentialAnagram isAnagramOf source

        // then
        assertThat(result)
            .isFalse()
    }

    @Test
    fun `Verify String#isAnagramOf classify listen - silent as anagram`() {
        // given
        val source = "Listen"
        val potentialAnagram = "Silent"

        // when
        val result = potentialAnagram isAnagramOf source

        // then
        assertThat(result)
            .isTrue()
    }

    @Test
    fun `Verify String#isAnagramOf correctly classify anagrams with whitespaces`() {
        // given
        val source = "New York Times"
        val potentialAnagram = "monkeys write"

        // when
        val result = potentialAnagram isAnagramOf source

        // then
        assertThat(result)
            .isTrue()
    }

    @Test
    fun `Verify String#isAnagramOf correctly classify anagrams with punctuation`() {
        // given
        val source = "She Sells Sanctuary"
        val potentialAnagram = "Santa; shy, less cruel"

        // when
        val result = potentialAnagram isAnagramOf source

        // then
        assertThat(result)
            .isTrue()
    }
}
