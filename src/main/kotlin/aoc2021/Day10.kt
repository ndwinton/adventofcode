package aoc2021

import java.util.*

fun findCorruption(line: String): String {
    val stack = Stack<Char>()
    val remainder = line.dropWhile { isValid(it, stack) }
    return if (remainder.isNotEmpty() && !isOpening(remainder.first())) remainder.first().toString() else ""
}

private fun isValid(ch: Char, stack: Stack<Char>): Boolean =
    if (isOpening(ch)) {
        stack.push(ch)
        true
    } else checkMatch(ch, stack)

private val matchForClosing = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
private fun checkMatch(ch: Char, stack: Stack<Char>): Boolean {
    return when {
        stack.empty() -> false
        stack.pop() == matchForClosing[ch] -> true
        else -> false
    }
}

private fun isOpening(ch: Char): Boolean =
    when (ch) {
        '(' -> true
        '[' -> true
        '<' -> true
        '{' -> true
        else -> false
    }

fun corruptionScore(lines: List<String>): Int =
    lines.map { findCorruption(it) }
        .groupingBy { it }
        .eachCount()
        .let { (it[")"] ?: 0) * 3 + (it["]"] ?: 0) * 57 + (it["}"] ?: 0) * 1197 + (it[">"] ?: 0) * 25137 }

fun completionString(line: String): String {
    val stack = Stack<Char>()
    line.dropWhile { isValid(it, stack) }
    return stack.toList().reversed().joinToString("") {
        when (it) {
            '(' -> ")"
            '[' -> "]"
            '{' -> "}"
            '<' -> ">"
            else -> ""
        }
    }
}

fun completionStringScore(str: String): Long =
    str.fold(0L) { score, ch -> score * 5 + "?)]}>".indexOf(ch) }

fun middleCompletionScore(lines: List<String>): Long =
    lines.filter { findCorruption(it) == "" }
        .map { completionStringScore(completionString(it)) }
        .sorted()
        .let { it[it.size / 2] }


