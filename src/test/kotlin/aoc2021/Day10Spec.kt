package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day10Spec : FunSpec({
    test("no corruption in empty string") {
        findCorruption("").shouldBe("")
    }

    test("given examples") {
        table(
            headers("input", "expected"),
            row("{([(<{}[<>[]}>{[]{[(<()>", "}"),
            row("[[<[([]))<([[{}[[()]]]", ")"),
            row("[{[{({}]{}}([{[{{{}}([]>", "]"),
            row("[<(<(<(<{}))><([]([]()", ")"),
            row("{<{([([[(<>()){}]>(<<{{", ">"),
        ).forAll { input, result -> findCorruption(input).shouldBe(result) }
    }

    test("no corruption in other lines") {
        table(
            headers("input", "expected"),
            row("[({(<(())[]>[[{[]{<()<>>", ""),
            row("[(()[<>])]({[<{<<[]>>(", ""),
            row("(((({<>}<{<{<>}{[]{[]{}", ""),
        ).forAll { input, result -> findCorruption(input).shouldBe(result) }
    }

    test("calculate corruption score") {
        corruptionScore("""
            [({(<(())[]>[[{[]{<()<>>
            [(()[<>])]({[<{<<[]>>(
            {([(<{}[<>[]}>{[]{[(<()>
            (((({<>}<{<{<>}{[]{[]{}
            [[<[([]))<([[{}[[()]]]
            [{[{({}]{}}([{[{{{}}([]
            {<[[]]>}<{[{[{[]{()[[[]
            [<(<(<(<{}))><([]([]()
            <{([([[(<>()){}]>(<<{{
            <{([{{}}[<[[[<>{}]]]>[]]
        """.trimIndent().lines()).shouldBe(26397)
    }

    test("completion strings") {
        table(
            headers("input", "expected"),
            row("", ""),
            row("()", ""),
            row("(", ")"),
            row("([", "])"),
            row("([]", ")"),
            row("[({(<(())[]>[[{[]{<()<>>", "}}]])})]")
        ).forAll { input, expected -> completionString(input).shouldBe(expected)}
    }

    test("completion string score") {
        completionStringScore("])}>").shouldBe(294L)
        completionStringScore("}}]])})]").shouldBe(288957L)
    }

    test("middle completions string score") {
        middleCompletionScore("""
            [({(<(())[]>[[{[]{<()<>>
            [(()[<>])]({[<{<<[]>>(
            {([(<{}[<>[]}>{[]{[(<()>
            (((({<>}<{<{<>}{[]{[]{}
            [[<[([]))<([[{}[[()]]]
            [{[{({}]{}}([{[{{{}}([]
            {<[[]]>}<{[{[{[]{()[[[]
            [<(<(<(<{}))><([]([]()
            <{([([[(<>()){}]>(<<{{
            <{([{{}}[<[[[<>{}]]]>[]]
        """.trimIndent().lines()).shouldBe(288957L)
    }
})