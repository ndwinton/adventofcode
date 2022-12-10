package aoc2022

// Simplify to a list of amounts to add at a given step, so "noop?" -> 0, "add 4" -> "0, 4"
fun simplifyInstructions(instructions: List<String>): List<Int> =
    listOf(1) + instructions.map {
        when (it) {
            "noop" -> listOf(0)
            else -> listOf(0, it.split(" ")[1].toInt())
        }
    }.flatten()

fun runInstructionsToStep(instructions: List<String>, step: Int) = simplifyInstructions(instructions).take(step).sum()

fun signalStrengthSums(instructions: List<String>) =
    listOf(20, 60, 100, 140, 180, 220).sumOf { runInstructionsToStep(instructions, it) * it }

fun drawPixels(instructions: List<String>) {
    (1 .. 240).map {
        val x = runInstructionsToStep(instructions, it)
        println("$it - $x")
        if (((it - 1) % 40) in (x - 1 .. x + 1)) "#" else "."
    }.joinToString("").chunked(40).forEach {println(it) }
}