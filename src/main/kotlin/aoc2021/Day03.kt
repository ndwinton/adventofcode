package aoc2021

object Day03 {
    fun gammaEpsilon(lines: List<String>): Pair<Int,Int> {
        val gammaString = lines.filter { it.isNotBlank() }
            .map { it.split("") }
            .transpose()
            .map { it.groupingBy { it }.eachCount() }
            .joinToString("") {
                val ones = it["1"] ?: -1
                val zeroes = it["0"] ?: -1
                when {
                    ones > 0 && ones >= zeroes -> "1"
                    zeroes > 0 && zeroes > ones -> "0"
                    else -> ""
                }
            }
        val epsilonString = gammaString.map { if (it == '1') '0' else '1' }.joinToString("")
        return Pair(gammaString.toInt(2), epsilonString.toInt(2))
    }

    fun gammaEpsilonProduct(lines: List<String>): Int = gammaEpsilon(lines).let { it.first * it.second }

}