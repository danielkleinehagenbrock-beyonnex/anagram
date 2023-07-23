package io.beyonnex.anagram.functions

fun CharArray.purgeWhitespacesAndPunctuation(): List<Char> = this
    .filter { !(it.isWhitespace() || it.isPunctuation()) }

private fun Char.isPunctuation(): Boolean = punctuationCharTypes.contains(Character.getType(this))

private val punctuationCharTypes: Set<Int> = setOf(
    Character.DASH_PUNCTUATION.toInt(),
    Character.START_PUNCTUATION.toInt(),
    Character.END_PUNCTUATION.toInt(),
    Character.CONNECTOR_PUNCTUATION.toInt(),
    Character.OTHER_PUNCTUATION.toInt(),
    Character.INITIAL_QUOTE_PUNCTUATION.toInt(),
    Character.FINAL_QUOTE_PUNCTUATION.toInt()
)
