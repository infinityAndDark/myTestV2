package com.example.tikitestv2.util

import java.text.Normalizer
import kotlin.math.abs

private const val SPACE: Char = ' '
private const val BREAK: Char = '\n'
private val REGEX_UN_ACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
private fun unAccent(str: String): String {
    val temp = Normalizer.normalize(str, Normalizer.Form.NFD)
    return REGEX_UN_ACCENT.replace(temp, "")
}

/**
 * Break new line for string
 */
fun String.breakLine(inputBuilder: StringBuilder): String {
    // create the list of words, each item value is length of word
    val origin = this
        .trim()
        .replace("\\s+".toRegex(), " ")

    val input = unAccent(origin)
    val words = input.split(SPACE).map { it.length }

    // if has 0 or 1 word, it's done
    if (words.size == 1 || words.isEmpty()) return this
    //if has 2 words, break new line
    if (words.size == 2) return this.replaceFirst(SPACE, BREAK)

    // if has more than 2 words, find the correct position to break new line
    val breakIndex = findBreakIndex(words, input.length)

    // break new line with position founded
    inputBuilder.clear()
    val listWords = origin.split(SPACE)
    inputBuilder.append(listWords[0])
    for (index in 1 until listWords.size) {
        if (index == breakIndex) inputBuilder.append(BREAK)
        else inputBuilder.append(SPACE)
        inputBuilder.append(listWords[index])
    }
    return inputBuilder.toString()
}

private fun findBreakIndex(
    words: List<Int>,
    inputLength: Int
): Int {
    var lineOne = words[0]
    var lineTwo = inputLength - (lineOne + 1)

    var breakIndex = 1
    while (breakIndex < words.size) {
        if (lineOne < lineTwo) {
            val preRange = lineTwo - lineOne
            lineOne += (words[breakIndex] + 1)
            lineTwo -= (words[breakIndex] + 1)
            if (lineOne == lineTwo || abs(lineOne - lineTwo) == 1) {
                breakIndex++
                break

            } else if (lineOne > lineTwo) {
                val newRange = abs(lineTwo - lineOne)
                if (preRange >= newRange) {
                    breakIndex++
                }
                break
            }
        } else break
        breakIndex++
    }
    return breakIndex
}