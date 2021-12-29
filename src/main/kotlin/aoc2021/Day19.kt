package aoc2021

data class Scanner(val name: String, val beacons: List<Beacon>) {
    val fingerprints = beacons.associateWith { beacon ->
        beacons.filter { it != beacon }.map { Pair(squareDistance(it, beacon), it) }.sortedBy { it.first }
    }

    private fun squareDistance(b1: Beacon, b2: Beacon) =
        (b2.position.x - b1.position.x) * (b2.position.x - b1.position.x) +
                (b2.position.y - b1.position.y) * (b2.position.y - b1.position.y) +
                (b2.position.z - b1.position.z) * (b2.position.z - b1.position.z)

    fun findOverlap(other: Scanner): List<Pair<Beacon, Beacon>> =
        fingerprints.flatMap { (beacon, list) ->
            val distances = list.map { it.first }
            val found = other.fingerprints.filter { (otherBeacon, otherList) ->
                val otherDistances = otherList.map { it.first }
                val intersect = distances.intersect(otherDistances.toSet())
                intersect.size >= 11
            }
            if (found.isEmpty()) emptyList() else found.keys.map { Pair(beacon, it) }
        }
}

data class Point3D(val x: Int, val y: Int, val z: Int)

data class Beacon(val name: String, val position: Point3D)

fun parseScanners(lines: List<String>, result: List<Scanner> = emptyList()): List<Scanner> {
    if (lines.isEmpty()) return result
    val name = lines.first().replace(Regex("\\s*-+\\s*"), "")
    val beacons = lines.drop(1)
        .takeWhile { it.isNotBlank() }
        .mapIndexed { index, line ->
            line.split(",").map { it.toInt() }.let { (x, y, z) -> Beacon("$name - $index", Point3D(x, y, z)) }
        }
    return parseScanners(lines.drop(beacons.size + 2), result + listOf(Scanner(name, beacons)))
}

fun findEquivalentBeacons(scanners: List<Scanner>, result: Map<Beacon,List<Beacon>> = emptyMap()): Map<Beacon,List<Beacon>> {
    if (scanners.isEmpty()) return result

    val current = scanners.first()
    val others = scanners.drop(1)
    val newMappings = others.flatMap { other -> current.findOverlap(other) }
        .groupBy { it.first }
        .map { (beacon, list) -> Pair(beacon, list.map { it.second }) }
        .toMap()
    return findEquivalentBeacons(others, result + newMappings)
}

fun uniqueBeacons(scanners: List<Scanner>): Int {
    val duplicated = findEquivalentBeacons(scanners).values.sumOf { it.size }
    val total = scanners.sumOf { it.beacons.size }
    return (total - duplicated)
}