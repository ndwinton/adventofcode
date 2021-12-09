package aoc2021

class HeightMap(lines: List<String>) {

    val width: Int = lines.first().length
    val height: Int = lines.size
    private val mapData = lines.map { it.map { ch -> ch.digitToInt() }.toIntArray() }.toTypedArray()

    fun isLocalMinimum(row: Int, col: Int): Boolean {
        val cell = safeGet(row, col)
        return cell < safeGet(row - 1, col) &&
                cell < safeGet(row + 1, col) &&
                cell < safeGet(row, col - 1) &&
                cell < safeGet(row, col + 1)
    }

    private fun safeGet(row: Int, col: Int): Int =
        if (row < 0 || row >= height || col < 0 || col >= width) 99 else mapData[row][col]

    fun sumRiskLevels(): Int =
        (0 until height).sumOf { row ->
            (0 until width).sumOf { col -> if (isLocalMinimum(row, col)) mapData[row][col] + 1 else 0 }
        }

}
