package io.beyonnex.anagram

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AnagramApplication

fun main(args: Array<String>) {
    runApplication<AnagramApplication>(*args)
}
