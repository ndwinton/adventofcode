package aoc2023

fun springArrangements(input: String): Int {
    val parts = input.split(" ")
    val pattern = parts[0] + "."
    val lengths = parts[1].split(",").map { it.toInt() }
    val result = permuteSprings("", pattern, lengths)
//    println(result.sorted())
//    val re = "^\\.*" + lengths.map { "#{$it}\\.+" }.joinToString("") + '$'
//    val regex = Regex(re)
//    result.sorted().filter { !it.matches(regex) }.forEach { println("$input ERROR $it") }
    return result.toSet().size
}

fun permuteSprings(head: String, tail: String, lengths: List<Int>, result: List<String> = emptyList()): List<String> {
    //println("permute: $head, $tail, $lengths, $result")
    if (lengths.isEmpty()) {
        return if (tail.contains('#')) result
        else result + (head + tail.replace('?', '.'))
    }

    val nextSize = lengths.first()
    if (tail.length < nextSize + 1) return result

    val chunk = tail.slice(0 .. nextSize)
    val nextResult = if (chunk[0] == '?') permuteSprings("$head.", tail.drop(1), lengths, result) else result

    return when {
        chunk.first() == '.' -> permuteSprings("$head.", tail.drop(1), lengths, nextResult)
        chunk.last() == '#' -> nextResult
        chunk.take(nextSize).all { it != '.' } -> {
            val extra = chunk.slice(0..chunk.length - 2).replace('?', '#') + "."
            permuteSprings(head + extra, tail.drop(nextSize + 1), lengths.drop(1), nextResult)
        }
        else -> nextResult
    }
}