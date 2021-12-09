package aoc2021

class HeightMap(lines: List<String>) {

    val width: Int = lines.first().length
    val height: Int = lines.size
    private val heights = lines.map { it.map { ch -> ch.digitToInt() }.toIntArray() }.toTypedArray()

    fun isLocalMinimum(row: Int, col: Int): Boolean {
        val height = getHeight(row, col)
        return height < getHeight(row - 1, col) &&
                height < getHeight(row + 1, col) &&
                height < getHeight(row, col - 1) &&
                height < getHeight(row, col + 1)
    }

    private fun getHeight(row: Int, col: Int): Int =
        if (row < 0 || row >= height || col < 0 || col >= width) 99 else heights[row][col]

    fun sumRiskLevels(): Int =
        (0 until height).sumOf { row ->
            (0 until width).sumOf { col -> if (isLocalMinimum(row, col)) heights[row][col] + 1 else 0 }
        }

    private val basinState = lines.map { row -> row.map { if (it == '9') -1 else 0 }.toIntArray() }.toTypedArray()

    private fun getBasin(row: Int, col: Int) =
        if (row < 0 || row >= height || col < 0 || col >= width) -1 else basinState[row][col]

    fun basinSizeProduct(): Int = basinSizes().sorted().takeLast(3).reduce { acc, i -> i * acc }

    fun basinSizes(): List<Int> {
        markBasins()
        return basinState
            .flatMap { row -> row.toList() }
            .filter { it > 0 }
            .groupingBy { it }
            .eachCount()
            .values.toList()
    }

    fun markBasins() {
        var currentBasin = 1
        (0 until height).forEach { row ->
            (0 until width).forEach { col ->
                if (getBasin(row, col) == 0) {
                    markNeighbours(row, col, currentBasin)
                    currentBasin++
                }
            }
        }
    }

    private fun markNeighbours(row: Int, col: Int, currentBasin: Int) {
        if (getBasin(row, col) != 0) return

        val above = getBasin(row - 1, col)
        val below = getBasin(row + 1, col)
        val left = getBasin(row, col - 1)
        val right = getBasin(row, col + 1)
        val neighbourMax = arrayOf(above, below, left, right).maxOf { it }

        basinState[row][col] = if (neighbourMax > 0) neighbourMax else currentBasin

        markNeighbours(row - 1, col, currentBasin)
        markNeighbours(row + 1, col, currentBasin)
        markNeighbours(row, col - 1, currentBasin)
        markNeighbours(row, col + 1, currentBasin)
    }
}
