package aoc2024

fun solvableCalibrationsTotal(data: List<String>, concat: Boolean = false): Long =
    data.sumOf { line ->
        val values = line.split(":", " ").filter { it != ""}.map { it.toLong() }
        if (isCalibrationSolvable(values.first(), values.drop(1).first(), values.drop(2), concat)) values.first() else 0
    }

private fun isCalibrationSolvable(target: Long, current: Long, remainder: List<Long>, concat: Boolean): Boolean = when {
    remainder.isEmpty() && current == target -> true
    remainder.isEmpty() && current != target -> false
    current > target -> false
    else -> isCalibrationSolvable(target, current * remainder.first(), remainder.drop(1), concat)
            || isCalibrationSolvable(target, current + remainder.first(), remainder.drop(1), concat)
            || (if (concat) isCalibrationSolvable(target, "${current}${remainder.first()}".toLong(), remainder.drop(1), concat) else false)
}
