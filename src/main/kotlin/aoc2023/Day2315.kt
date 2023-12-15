package aoc2023

fun String.lavaHash() = this.fold(0) { acc, c -> ((acc + c.code) * 17) % 256 }

fun initSequenceTotal(input: String) = input.trim().split(",").sumOf { it.lavaHash() }

fun buildHashMap(data: String): List<MutableMap<String, Int>> {
    val hashMap: List<MutableMap<String, Int>> = List(256) { mutableMapOf() }
    data.split(",").map {
        val key = it.split("-", "=")[0]
        val hash = key.lavaHash()
        if (it.contains('-')) hashMap[hash].remove(key)
        if (it.contains('=')) {
            val value = it.split("=")[1].toInt()
            hashMap[hash][key] = value
        }
    }
    return hashMap
}

fun focusingPower(data: String): Int =
    buildHashMap(data).mapIndexed { index, map ->
        (index + 1) * map.values.mapIndexed { slot, flen -> (1 + slot) * flen }.sum()
    }.sum()