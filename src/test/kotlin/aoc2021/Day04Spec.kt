package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day04Spec : FunSpec({
    val data = """7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7""".lines()

    val singleBoardLines = listOf(
        "14 21 17 24  4",
        "10 16 15  9 19",
        "18  8 23 26 20",
        "22 11 13  6  5",
        " 2  0 12  3  7"
    )

    val board = Board(singleBoardLines)

    test("parse sequence from input") {
        Day04.parseCallList(data).shouldBe(listOf(7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1))
    }

    test("parsing individual board") {
        val board = Board(singleBoardLines)

        board[0, 0].shouldBe(Cell(14, false))
        board[1, 1].shouldBe(Cell(16,false))
        board[4, 0].shouldBe(Cell(2, false))
        board[4, 3].shouldBe(Cell(3, false))
    }

    test("calling a number on the board") {
        Board(singleBoardLines).let {
            it[2, 2].shouldBe(Cell(23, false))
            it.call(23)
            it[2, 2].shouldBe(Cell(23, true))
        }
    }

    test("calling a number NOT on the board") {
        Board(singleBoardLines).let {
            it.call(42)
            (0 .. 4).forEach { row ->
                (0..4).forEach { col -> it[row, col].called.shouldBe(false) }
            }
        }
    }

    test("board is not initially in a winning state") {
        Board(singleBoardLines).hasWon().shouldBe(false)
    }

    test("winning row") {
        val board = Board(singleBoardLines)
        listOf(9, 10, 15, 16).forEach {
            board.call(it)
            board.hasWon().shouldBe(false)
        }
        board.call(19)
        board.hasWon().shouldBe(true)
    }

    test("winning column") {
        val board = Board(singleBoardLines)
        listOf(3, 6, 9, 24).forEach {
            board.call(it)
            board.hasWon().shouldBe(false)
        }
        board.call(26)
        board.hasWon().shouldBe(true)
    }

    test("score of non-winning board is zero") {
        Board(singleBoardLines).score().shouldBe(0)
    }

    test("calculate board score for winning board") {
        val board = Board(singleBoardLines)
        listOf(3, 6, 9, 24, 26).forEach { board.call(it) }
        board.score().shouldBe(6682)
    }

    test("parse to list of boards") {
        val boards = Day04.parseBoards(data)
        boards.map {it[0, 0] }.shouldBe(listOf(Cell(22), Cell(3), Cell(14)))
    }

    test("finding the winner") {
        Day04.findWinningScore(data).shouldBe(4512)
    }

    test("finding the last winner") {
        Day04.findLastWinningScore(data).shouldBe(1924)
    }

})