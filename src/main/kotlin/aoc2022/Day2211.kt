package aoc2022

typealias Op = (Long) -> Long
class Monkey(val id: Int = -1, val items: MutableList<Long> = mutableListOf(), val op: Op = { it }, val divisor: Int = 1, val onTrue: Int = -1, val onFalse: Int = -1) {
    var inspections = 0

    override fun toString(): String {
        return "Monkey $id: inpections = $inspections, items = $items"
    }
}

fun parseMonkeyText(text: String): Monkey {
    val lines = text.split("\n")
    val id = lines[0].trim().split(":", " ")[1].toInt()
    val items = lines[1].split(":")[1].trim().split(" ", ",").filter { it.isNotEmpty() }.map { it.toLong() }
    val op = parseOp(lines[2])
    val divisor = lines[3].trim().split(" ").last().toInt()
    val onTrue = lines[4].trim().split(" ").last().toInt()
    val onFalse = lines[5].trim().split(" ").last().toInt()
    return Monkey(id, items.toMutableList(), op, divisor, onTrue, onFalse)
}

private fun parseOp(line: String): Op {
    if (line.contains("old * old")) return { it * it }
    val param = line.split(" ").last().toInt()
    if (line.contains("old +")) {
        return { it + param }
    }
    return { it * param }
}

fun parseAllMonkeys(text: String) = text.split("\n\n").map { parseMonkeyText(it) }

fun runRound(monkeys: List<Monkey>, limitFactor: Int = 3, limitMod: Int = Int.MAX_VALUE) {
    monkeys.forEach { monkey ->
        monkey.inspections += monkey.items.size
        monkey.items.forEach { item ->
            val newItem = (monkey.op.invoke(item) / limitFactor) % limitMod
            if (newItem % monkey.divisor == 0L) monkeys[monkey.onTrue].items.add(newItem)
            else monkeys[monkey.onFalse].items.add(newItem)
        }
        monkey.items.removeAll { true }
    }
}

fun monkeyBusiness(text: String, rounds: Int = 20, limitFactor: Int = 3): Long {
    val monkeys = parseAllMonkeys(text)
    val limitMod = monkeys.fold(1) { acc, monkey -> acc * monkey.divisor }
    (1 .. rounds).forEach { runRound(monkeys, limitFactor, limitMod) }
    return monkeys.map { it.inspections }.sortedDescending().take(2).let { it[0].toLong() * it[1].toLong() }
}

