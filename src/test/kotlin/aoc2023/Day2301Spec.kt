package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day2301Spec : FunSpec ({
    val example = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
        """.trimIndent()

    test("calibration value") {
        calibrationValueOfLine("1abc2").shouldBe(12)
        calibrationValueOfLine("pqr3stu8vwx").shouldBe(38)
        calibrationValueOfLine("a1b2c3d4e5f").shouldBe(15)
        calibrationValueOfLine("treb7uchet").shouldBe(77)
    }

    test("transform first number word") {
        table(
            headers("input", "output"),
            row("two1nine", "2wo1nine"),
            row("eightwothree", "8ightwothree"),
            row("abcone2threexyz", "abc1ne2threexyz"),
            row("xtwone3four", "x2wone3four"),
            row("4nineeightseven2", "49ineeightseven2"),
            row("zoneight234", "z1neight234"),
            row("7pqrstsixteen", "7pqrst6ixteen"),
            row("8kgplfhvtvqpfsblddnineoneighthg", "8kgplfhvtvqpfsbldd9ineoneighthg"),
        ).forAll { input, output -> transformFirstNumberWord(input).shouldBe(output) }
    }

    test("transform all number words") {
        table(
            headers("input", "output"),
            row("two1nine", "2wo19ine"),
            row("eightwothree", "8igh2wo3hree"),
            row("abcone2threexyz", "abc1ne23hreexyz"),
            row("xtwone3four", "x2w1ne34our"),
            row("4nineeightseven2", "49ine8ight7even2"),
            row("zoneight234", "z1n8ight234"),
            row("7pqrstsixteen", "7pqrst6ixteen"),
            row("8kgplfhvtvqpfsbldd9oneighthg", "8kgplfhvtvqpfsbldd91n8ighthg"),
            row("eighthree", "8igh3hree"),
            row("sevenine", "7eve9ine"),

        ).forAll { input, output -> transformAllNumberWords(input).shouldBe(output) }
    }

    test("calibration value with words") {
        table(
            headers("input", "output"),
            row("21nine", 29),
            row("8wothree", 83),
            row("abc12threexyz", 13),
            row("x2ne3four", 24),
            row("49eightseven2", 42),
            row("z1ight234", 14),
            row("7pqrst6teen", 76)
        ).forAll { input, output -> calibrationValueWithWords(input).shouldBe(output) }
    }
})