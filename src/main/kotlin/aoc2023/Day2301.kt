package aoc2023

import java.util.StringJoiner

fun calibrationValueOfLine(line: String) =
    line.filter { it.isDigit() }.let { it.first().toString() + it.last().toString() }.toInt()

fun calibrationValueWithWords(line: String) = calibrationValueOfLine(transformAllNumberWords(line))

fun transformFirstNumberWord(line: String) =
    line.replace(Regex("^(.*?)(one|two|three|four|five|six|seven|eight|nine)")) { match ->
        match.groupValues[1] + mapOf(
            "one" to "1ne",
            "two" to "2wo",
            "three" to "3hree",
            "four" to "4our",
            "five" to "5ive",
            "six" to "6ix",
            "seven" to "7even",
            "eight" to "8ight",
            "nine" to "9ine"
        ).getOrDefault(match.groupValues[2], "?")
    }

fun transformAllNumberWords(line: String): String = transformFirstNumberWord(line).let {
    if (it == line) line else transformAllNumberWords(it)
}