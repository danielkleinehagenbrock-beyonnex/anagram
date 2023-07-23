package io.beyonnex.anagram.functions

import assertk.all
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import org.junit.jupiter.api.Test

class CharArrayExtensionsTest {
    @Test
    fun `Verify CharArray#purgeWhitespacesAndPunctuation leaves textual CharArrays untouched`() {
        // given
        val text = "abc"
        val charArray = text.toCharArray()

        // when
        val actual = charArray.purgeWhitespacesAndPunctuation()

        // then
        assertThat(actual).all {
            hasSize(3)
            containsExactly('a', 'b', 'c')
        }
    }

    @Test
    fun `Verify CharArray#purgeWhitespacesAndPunctuation removes punctuation chars`() {
        // given
        val somePunctuationChars = ".,;:?!-_\"'".toCharArray()

        // when
        val actual = somePunctuationChars.purgeWhitespacesAndPunctuation()

        // then
        assertThat(actual)
            .isEmpty()
    }
}
