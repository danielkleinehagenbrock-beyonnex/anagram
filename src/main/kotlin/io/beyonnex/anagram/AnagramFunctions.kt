package io.beyonnex.anagram

import org.slf4j.Logger
import org.slf4j.LoggerFactory

infix fun String.isAnagramOf(other: String): Boolean = this.countCharacters() == other.countCharacters()

private fun String.countCharacters(): Map<Char, Long> =
    with(mutableMapOf<Char, Long>()) {
        this@countCharacters
            .lowercase()
            .toCharArray()
            .purgeWhitespacesAndPunctuation()
            .forEach { char ->
                this[char] = (this[char] ?: 0L).inc()
            }
        logger.debug("${this@countCharacters} associated to $this")
        this
    }

private val logger: Logger = LoggerFactory.getLogger(AnagramApplication::class.java)
