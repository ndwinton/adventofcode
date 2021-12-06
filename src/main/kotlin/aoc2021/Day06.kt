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