package aoc2021

typealias Sheet = List<List<Int>>

fun buildSheet(lines: List<String>): Sheet {
    val dimensions = lines.filter { it.startsWith("fold") }
        .map { line -> line.split("=")
            .let { if (it[0].endsWith("x")) Pair(0, it[1].toInt()) else Pair(it[1].toInt(), 0) }
        }
    val maxRow = dimensions.maxOf { it.first } * 2
    val maxCol = dimensions.maxOf { it.second } * 2

    return lines.filter { it.matches(Regex("^[0-9].*")) }
        .map { line -> line.split(",").let { Pair(it[1].toInt(), it[0].toInt()) } }
        .let { pairs ->
            (0..maxRow).map { row ->
                (0..maxCol).map { col -> if (pairs.contains(Pair(row, col))) 1 else 0 }
            }
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

fun runFolds(lines: List<String>): Sheet =
    lines.filter { it.startsWith("fold") }
        .fold(buildSheet(lines)) { sheet, line -> if (line.startsWith("fold along y")) foldHorizontally(sheet) else foldVertically(sheet)}

fun printableSheet(sheet: Sheet): String =
    sheet.joinToString("\n") { row -> row.joinToString("") { (if (it > 0) "[]" else "  ") } }