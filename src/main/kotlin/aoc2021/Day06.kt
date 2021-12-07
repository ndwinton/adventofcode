package aoc2021

data class Fish(val timer: Int) {
    fun evolve(): List<Fish> =
        if (timer == 0) listOf(Fish(6), Fish(8)) else listOf(Fish(timer - 1))
}

class Shoal(vararg val states: Int) {
    fun evolve(): Shoal {
        val oldPlusNew = fishes
            .flatMap { it.evolve() }
            .map { it.timer }
            .partition { it < 8 }
        return Shoal(*oldPlusNew.first.toIntArray(), *oldPlusNew.second.toIntArray())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Shoal

        if (!states.contentEquals(other.states)) return false

        return true
    }

    override fun hashCode(): Int {
        return states.contentHashCode()
    }

    override fun toString(): String {
        return "Shoal(states=${states.contentToString()})"
    }

    val fishes get() = states.map { Fish(it) }
}

tailrec fun simulateShoal(remaining: Int, shoal: Shoal): Int =
    if (remaining == 0) shoal.states.count()
    else simulateShoal(remaining - 1, shoal.evolve())

data class CountedShoal(val stateCounts: Map<Int,Long>) {
    fun evolve(): CountedShoal {
        return CountedShoal(mapOf(
            0 to (stateCounts[1] ?: 0),
            1 to (stateCounts[2] ?: 0),
            2 to (stateCounts[3] ?: 0),
            3 to (stateCounts[4] ?: 0),
            4 to (stateCounts[5] ?: 0),
            5 to (stateCounts[6] ?: 0),
            6 to (stateCounts[7] ?: 0) + (stateCounts[0] ?: 0),
            7 to (stateCounts[8] ?: 0),
            8 to (stateCounts[0] ?: 0),
        ))
    }
}

tailrec fun simulateCountedShoal(remaining: Int, shoal: CountedShoal): Long =
    if (remaining == 0) shoal.stateCounts.values.sum()
    else simulateCountedShoal(remaining - 1, shoal.evolve())
