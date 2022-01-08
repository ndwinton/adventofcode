package aoc2021

import kotlin.math.abs

data class Scanner(val name: String, val beacons: List<Beacon>, val position: Point3D = Point3D(0, 0, 0)) {
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
            val found = other.fingerprints.filter { (_, otherList) ->
                val otherDistances = otherList.map { it.first }
                val intersect = distances.intersect(otherDistances.toSet())
                intersect.size >= 11
            }
            if (found.isEmpty()) emptyList() else found.keys.map { Pair(beacon, it) }
        }
}

typealias Tuple3 = Point3D

data class Point3D(val x: Int, val y: Int, val z: Int) {
    operator fun times(m: Matrix3): Tuple3 =
        Tuple3(
            x * m.r1c1 + y * m.r1c2 + z * m.r1c3,
            x * m.r2c1 + y * m.r2c2 + z * m.r2c3,
            x * m.r3c1 + y * m.r3c2 + z * m.r3c3
        )

    operator fun minus(other: Point3D): Tuple3 = Tuple3(x - other.x, y - other.y, z - other.z)

    fun manhattan(other: Point3D): Int = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
}

data class Matrix3(val r1c1: Int, val r1c2: Int, val r1c3: Int,
                   val r2c1: Int, val r2c2: Int, val r2c3: Int,
                   val r3c1: Int, val r3c2: Int, val r3c3: Int)

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

fun findScannerForBeacon(scanners: List<Scanner>, beacon: Beacon): Scanner =
    scanners.find { it.beacons.contains(beacon) }!!

val transforms = listOf(
    // 100/010/001
    Matrix3(
        1, 0, 0,
        0, 1, 0,
        0, 0, 1
    ),
    Matrix3(
        -1, 0, 0,
        0, 1, 0,
        0, 0, 1
    ),
    Matrix3(
        1, 0, 0,
        0, -1, 0,
        0, 0, 1
    ),
    Matrix3(
        1, 0, 0,
        0, 1, 0,
        0, 0, -1
    ),
    Matrix3(
        -1, 0, 0,
        0, -1, 0,
        0, 0, 1
    ),
    Matrix3(
        -1, 0, 0,
        0, 1, 0,
        0, 0, -1
    ),
    Matrix3(
        1, 0, 0,
        0, -1, 0,
        0, 0, -1
    ),
    Matrix3(
        -1, 0, 0,
        0, -1, 0,
        0, 0, -1
    ),
    // 100/001/010
    Matrix3(
        1, 0, 0,
        0, 0, 1,
        0, 1, 0
    ),
    Matrix3(
        -1, 0, 0,
        0, 0, 1,
        0, 1, 0
    ),
    Matrix3(
        1, 0, 0,
        0, 0, 1,
        0, -1, 0
    ),
    Matrix3(
        1, 0, 0,
        0, 0, -1,
        0, 1, 0
    ),
    Matrix3(
        -1, 0, 0,
        0, 0, 1,
        0, -1, 0
    ),
    Matrix3(
        -1, 0, 0,
        0, 0, -1,
        0, 1, 0
    ),
    Matrix3(
        1, 0, 0,
        0, 0, -1,
        0, -1, 0
    ),
    Matrix3(
        -1, 0, 0,
        0, 0, -1,
        0, -1, 0
    ),
    // 010/100/001
    Matrix3(
        0, 1, 0,
        1, 0, 0,
        0, 0, 1
    ),
    Matrix3(
        0, 1, 0,
        -1, 0, 0,
        0, 0, 1
    ),
    Matrix3(
        0, -1, 0,
        1, 0, 0,
        0, 0, 1
    ),
    Matrix3(
        0, 1, 0,
        1, 0, 0,
        0, 0, -1
    ),
    Matrix3(
        0, -1, 0,
        -1, 0, 0,
        0, 0, 1
    ),
    Matrix3(
        0, 1, 0,
        -1, 0, 0,
        0, 0, -1
    ),
    Matrix3(
        0, -1, 0,
        1, 0, 0,
        0, 0, -1
    ),
    Matrix3(
        0, -1, 0,
        -1, 0, 0,
        0, 0, -1
    ),
    // 001/100/010
    Matrix3(
        0, 0, 1,
        1, 0, 0,
        0, 1, 0
    ),
    Matrix3(
        0, 0, 1,
        -1, 0, 0,
        0, 1, 0
    ),
        Matrix3(
        0, 0, 1,
        1, 0, 0,
        0, -1, 0
    ),
    Matrix3(
        0, 0, -1,
        1, 0, 0,
        0, 1, 0
    ),
    Matrix3(
        0, 0, -1,
        1, 0, 0,
        0, -1, 0
    ),
    Matrix3(
        0, 0, 1,
        -1, 0, 0,
        0, -1, 0
    ),
    Matrix3(
        0, 0, -1,
        -1, 0, 0,
        0, 1, 0
    ),
    Matrix3(
        0, 0, -1,
        -1, 0, 0,
        0, -1, 0
    ),
    // 001/010/100
    Matrix3(
        0, 0, 1,
        0, 1, 0,
        1, 0, 0
    ),
    Matrix3(
        0, 0, 1,
        0, 1, 0,
        -1, 0, 0
    ),
    Matrix3(
        0, 0, 1,
        0, -1, 0,
        1, 0, 0
    ),
    Matrix3(
        0, 0, -1,
        0, 1, 0,
        1, 0, 0
    ),
    Matrix3(
        0, 0, 1,
        0, -1, 0,
        -1, 0, 0
    ),
    Matrix3(
        0, 0, -1,
        0, 1, 0,
        -1, 0, 0
    ),
    Matrix3(
        0, 0, -1,
        0, -1, 0,
        1, 0, 0
    ),
    Matrix3(
        0, 0, -1,
        0, -1, 0,
        -1, 0, 0
    ),
    // 010/001/100
    Matrix3(
        0, 1, 0,
        0, 0, 1,
        1, 0, 0
    ),
    Matrix3(
        0, 1, 0,
        0, 0, 1,
        -1, 0, 0
    ),
    Matrix3(
        0, -1, 0,
        0, 0, 1,
        1, 0, 0
    ),
    Matrix3(
        0, 1, 0,
        0, 0, -1,
        1, 0, 0
    ),
    Matrix3(
        0, -1, 0,
        0, 0, 1,
        -1, 0, 0
    ),
    Matrix3(
        0, 1, 0,
        0, 0, -1,
        -1, 0, 0
    ),
    Matrix3(
        0, -1, 0,
        0, 0, -1,
        1, 0, 0
    ),
    Matrix3(
        0, -1, 0,
        0, 0, -1,
        -1, 0, 0
    ),
)

fun normaliseScanners(normalised: List<Scanner>, remaining: List<Scanner>): List<Scanner> {
    if (remaining.isEmpty()) return normalised

    val overlaps = normalised.associateWith { norm -> remaining.map { norm.findOverlap(it) } }
    // overlaps is a map of normalised scanner to a list containing a sub-list for each of the
    // non-normalised scanners. These sub-lists contain pairs for corresponding normalised/non-normalised
    // beacons

    val oldNewPairs = overlaps.flatMap { (scanner, beaconPairLists) ->
        beaconPairLists
            .filter { it.isNotEmpty() }
            .map { createScannerWithRemappedBeacons(remaining, it) }
    }
    val old = oldNewPairs.map { it.first }
    val new = oldNewPairs.map { it.second }
    return normaliseScanners(normalised + new, remaining - old)
}

private fun createScannerWithRemappedBeacons(remaining: List<Scanner>, pairList: List<Pair<Beacon,Beacon>>): Pair<Scanner, Scanner> {
    val normalVector = pairList[1].first.position - pairList[0].first.position
    val nonNormalVector = pairList[1].second.position - pairList[0].second.position
    val transform = findTransform(normalVector, nonNormalVector)
    val offset = (pairList[0].second.position * transform) - pairList[0].first.position
    val nonNormalScanner = findScannerForBeacon(remaining, pairList[0].second)
    val newScanner = Scanner(
        nonNormalScanner.name,
        nonNormalScanner.beacons.map { beacon -> Beacon(beacon.name, beacon.position * transform - offset) },
        Point3D(0, 0, 0) - offset
    )
    return Pair(nonNormalScanner, newScanner)
}

fun findTransform(normalised: Tuple3, nonNormalised: Tuple3): Matrix3 {
    return transforms.find { nonNormalised * it == normalised }!!
}

fun maxScannerManhattan(scanners: List<Scanner>): Int =
    normaliseScanners(scanners.take(1), scanners.drop(1)).let { normalised ->
        normalised.cartesianProduct(normalised).map { it.first.position.manhattan(it.second.position) }.maxOf { it }
    }