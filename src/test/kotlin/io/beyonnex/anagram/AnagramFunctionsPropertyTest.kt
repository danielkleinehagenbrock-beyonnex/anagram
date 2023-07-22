package io.beyonnex.anagram

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.of
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class AnagramFunctionsPropertyTest : StringSpec(
    {
        "Verify two random string are no anagrams of each other" {
            checkAll(
                iterations = 10_000,
                genA = Arb.string(minSize = 0, maxSize = 100),
                genB = Arb.string(minSize = 101, maxSize = 200)
            ) { a, b -> (a isAnagramOfVerifiedBySorting b) shouldBe false }
        }
        "Verify a random shuffled string is a anagram of the original" {
            checkAll(
                iterations = 10_000,
                genA = generateAnagram()
            ) { (a, b) ->
                (a isAnagramOfVerifiedBySorting b) shouldBe true
            }
        }
    }
)

private fun generateAnagram(): Arb<Pair<String, String>> = arbitrary {
    val string = Arb.string().next(it)
    val punctuationAndWhitespaces: List<Char> = generatePunctuationAndWhitespaces().next(it)
    val shuffled = string.toCharArray()
        .asList()
        .plus(punctuationAndWhitespaces)
        .shuffled().joinToString()
    string to shuffled
}

private fun generatePunctuationAndWhitespaces() = Arb.list(
    gen = Arb.of(' ', '\t', '\n', '\r', '.', ',', ';', ':', '?', '!', '-', '_', '"', '\''),
    range = 0..10
)
