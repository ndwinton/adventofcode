package aoc2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2207Spec : FunSpec({

    val input = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent().split("\n")

    test("build simple tree with only directories") {
        buildDirectoryTree(listOf("\$ cd /", "dir a", "dir b", "\$ cd a", "dir c")).shouldBe(
            Dir("/", mutableListOf(
                Dir("a", mutableListOf(Dir("c"))),
                Dir("b"))))
    }

    test("build tree from sample data") {
        buildDirectoryTree(input).toString().shouldBe("/ 48381165 [a 94853 [e 584 []], d 24933642 []]")
    }

    test("find all dirs") {
        buildDirectoryTree(input).findAllDirs().size.shouldBe(4)
    }

    test("sum <= 100k") {
        sumOfDirsUnder100k(input).shouldBe(95437L)
    }

    test("find deletion candidate") {
        findDeletionCandidate(input).shouldBe(24933642L)
    }
})

