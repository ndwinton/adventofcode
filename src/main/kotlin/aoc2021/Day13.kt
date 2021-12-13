package aoc2021

typealias Sheet = List<List<Int>>
fun buildSheet(lines: List<String>): Sheet =
    lines.filter { it.matches(Regex("^[0-9].*")) }
        .map { line -> line.split(",").let { Pair(it[1].toInt(), it[0].toInt()) } }
        .let { pairs ->
            val maxRow = pairs.maxOf { it.first }
            val maxCol = pairs.maxOf { it.second }
            (0 .. maxRow).map { row ->
                (0 .. maxCol).map { col -> if (pairs.contains(Pair(row, col))) 1 else 0 }
            }
        }

fun foldHorizontally(sheet: Sheet): Sheet =
    sheet.take(sheet.size / 2)
        .zip(sheet.takeLast(sheet.size / 2).reversed())
        .map { it.first.zip(it.second) }
        .map { it.map { pair -> pair.first + pair.second }}

fun foldVertically(sheet: Sheet): Sheet = foldHorizontally(sheet.transpose()).transpose()

fun runFirstFold(lines: List<String>): Sheet =
    lines.filter { it.startsWith("fold") }
        .take(1)
        .fold(buildSheet(lines)) { sheet, line -> if (line.startsWith("fold along y")) foldHorizontally(sheet) else foldVertically(sheet)}

fun countDots(sheet: Sheet) = sheet.sumOf { row -> row.count { it > 0 } }