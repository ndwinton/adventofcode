package aoc2021

object Day03 {
    fun gammaEpsilon(lines: List<String>): Pair<Int,Int> =
        Pair(gammaDigits(lines).toInt(2), epsilonDigits(lines).toInt(2))

    private fun gammaDigits(lines: List<String>) =
        lines.filter { it.isNotBlank() }
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

    private fun epsilonDigits(lines: List<String>) = gammaDigits(lines).map { if (it == '1') '0' else '1' }.joinToString("")

    fun gammaEpsilonProduct(lines: List<String>): Int = gammaEpsilon(lines).let { it.first * it.second }

    fun o2Generator(inputs: List<String>): String =
        findSingleMatchingPrefix(0, inputs, this::gammaDigits)

    fun co2Scrubber(inputs: List<String>) =
        findSingleMatchingPrefix(0, inputs, this::epsilonDigits)

    fun findSingleMatchingPrefix(offset: Int, inputs: List<String>, function: (inputs: List<String>) -> String): String {
        val prefix = function(inputs).substring(0, offset + 1)
        return inputs.partition { it[offset] == prefix[offset] }.first.let {
            if (it.size == 1) it.first()
            else findSingleMatchingPrefix(offset + 1, it, function)
        }
    }

    fun gasesProduct(lines: List<String>) = o2Generator(lines).toInt(2) * co2Scrubber(lines).toInt(2)
}