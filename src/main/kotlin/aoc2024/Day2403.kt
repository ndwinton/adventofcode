package aoc2024

fun cleanAndExecute(memory: String) =
    Regex("""mul\((\d+),(\d+)\)""")
        .findAll(memory)
        .sumOf { matchResult -> matchResult.groupValues[1].toInt() * matchResult.groupValues[2].toInt() }

fun cleanAndExecuteWithConditions(memory: String) : Int =
    memory.split(Regex("""don't\(\)"""))
        .let { parts ->
            cleanAndExecute(parts.first()) + parts.drop(1).sumOf {
                cleanAndExecute(it.replaceBefore("do()", "", ""))
            }
        }