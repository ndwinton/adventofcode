package aoc2021

object Day02 {
    // Part 1

    tailrec fun move(moves: List<String>, position: Pair<Int, Int>): Pair<Int, Int> =
        if (moves.isEmpty()) position
        else move(moves.drop(1), Pair(position.first + forward(moves.first()), position.second + upDown(moves.first())))

    private fun upDown(instruction: String): Int =
        when {
            (instruction.startsWith("down")) -> instruction.split(" ")[1].toInt()
            (instruction.startsWith("up")) -> -(instruction.split(" ")[1].toInt())
            else -> 0
        }

    private fun forward(instruction: String): Int =
        if (instruction.startsWith("forward")) instruction.split(" ")[1].toInt() else 0

    fun moveProduct(moves: List<String>) = move(moves, Pair(0, 0)).let { it.first * it.second }

    // Part 2

    data class Position(val horizontal: Int, val vertical: Int, val aim: Int)

    fun moveWithAim(moves: List<String>, position: Position): Position =
        if (moves.isEmpty()) position
        else moveWithAim(
            moves.drop(1),
            Position(
                position.horizontal + forward(moves.first()),
                position.vertical + aiming(moves.first(), position.aim),
                position.aim + upDown(moves.first())
            )
        )

    private fun aiming(instruction: String, aim: Int): Int =
        if (instruction.startsWith("forward")) instruction.split(" ")[1].toInt() * aim else 0

    fun moveWithAimProduct(moves: List<String>) =
        moveWithAim(moves, Position(0, 0, 0)).let { it.horizontal * it.vertical }
}