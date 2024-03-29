package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe

class Day19Spec : FunSpec({
    val example = """
        --- scanner 0 ---
        404,-588,-901
        528,-643,409
        -838,591,734
        390,-675,-793
        -537,-823,-458
        -485,-357,347
        -345,-311,381
        -661,-816,-575
        -876,649,763
        -618,-824,-621
        553,345,-567
        474,580,667
        -447,-329,318
        -584,868,-557
        544,-627,-890
        564,392,-477
        455,729,728
        -892,524,684
        -689,845,-530
        423,-701,434
        7,-33,-71
        630,319,-379
        443,580,662
        -789,900,-551
        459,-707,401

        --- scanner 1 ---
        686,422,578
        605,423,415
        515,917,-361
        -336,658,858
        95,138,22
        -476,619,847
        -340,-569,-846
        567,-361,727
        -460,603,-452
        669,-402,600
        729,430,532
        -500,-761,534
        -322,571,750
        -466,-666,-811
        -429,-592,574
        -355,545,-477
        703,-491,-529
        -328,-685,520
        413,935,-424
        -391,539,-444
        586,-435,557
        -364,-763,-893
        807,-499,-711
        755,-354,-619
        553,889,-390

        --- scanner 2 ---
        649,640,665
        682,-795,504
        -784,533,-524
        -644,584,-595
        -588,-843,648
        -30,6,44
        -674,560,763
        500,723,-460
        609,671,-379
        -555,-800,653
        -675,-892,-343
        697,-426,-610
        578,704,681
        493,664,-388
        -671,-858,530
        -667,343,800
        571,-461,-707
        -138,-166,112
        -889,563,-600
        646,-828,498
        640,759,510
        -630,509,768
        -681,-892,-333
        673,-379,-804
        -742,-814,-386
        577,-820,562

        --- scanner 3 ---
        -589,542,597
        605,-692,669
        -500,565,-823
        -660,373,557
        -458,-679,-417
        -488,449,543
        -626,468,-788
        338,-750,-386
        528,-832,-391
        562,-778,733
        -938,-730,414
        543,643,-506
        -524,371,-870
        407,773,750
        -104,29,83
        378,-903,-323
        -778,-728,485
        426,699,580
        -438,-605,-362
        -469,-447,-387
        509,732,623
        647,635,-688
        -868,-804,481
        614,-800,639
        595,780,-596

        --- scanner 4 ---
        727,592,562
        -293,-554,779
        441,611,-461
        -714,465,-776
        -743,427,-804
        -660,-479,-426
        832,-632,460
        927,-485,-438
        408,393,-506
        466,436,-512
        110,16,151
        -258,-428,682
        -393,719,612
        -211,-452,876
        808,-476,-593
        -575,615,604
        -485,667,467
        -680,325,-822
        -627,-443,-432
        872,-547,-609
        833,512,582
        807,604,487
        839,-516,451
        891,-625,532
        -652,-548,-490
        30,-46,-14
    """.trimIndent().lines()

    test("parse input") {
        val scanners = parseScanners(example)
        scanners.size.shouldBe(5)
        scanners[4].name.shouldBe("scanner 4")
        scanners.map { it.beacons.size }.shouldContainInOrder(25, 25, 26, 25, 26)
    }

    test("beacon fingerprints") {
        val scanner = parseScanners("""
            --- 0 ---
            1,1,1
            2,2,2
            4,4,4
        """.trimIndent().lines()).first()
        scanner.fingerprints.shouldBe(mapOf(
            scanner.beacons[0] to listOf(Pair(3, scanner.beacons[1]), Pair(27, scanner.beacons[2])),
            scanner.beacons[1] to listOf(Pair(3, scanner.beacons[0]), Pair(12, scanner.beacons[2])),
            scanner.beacons[2] to listOf(Pair(12, scanner.beacons[1]), Pair(27, scanner.beacons[0]))
        ))
    }

    test("find overlaps") {
        val scanners = parseScanners(example)

        val overlap = scanners[0].findOverlap(scanners[1])
        overlap.size.shouldBe(12)
        overlap.map { it.first }.shouldContainInOrder(scanners[0].beacons[0], scanners[0].beacons[9], scanners[0].beacons[24])
    }

    test("finding equivalents") {
        val scanners = parseScanners(example)

        val result = findEquivalentBeacons(scanners)
        result.size.shouldBe(42)
    }

    test("unique beacons") {
        val scanners = parseScanners(example)
        uniqueBeacons(scanners).shouldBe(79)
    }

    test("matrix multiplication") {
        (Tuple3(1, 2, 3) * Matrix3(
            1, 2, 3,
            -1, -2, -3,
            10, 20, 30
        )).shouldBe(Tuple3(14, -14, 140))
    }

    test("point subtraction") {
        (Point3D(10, 20, 30) - Point3D(1, 2, 3)).shouldBe(Tuple3(9, 18, 27))
    }

    test("finding scanner for beacon") {
        val scanners = parseScanners(example)

        findScannerForBeacon(scanners.toSet(), scanners[2].beacons[3]).shouldBe(scanners[2])
    }

    test("normalising") {
        val scanners = parseScanners(example)
        val normalised = normaliseScanners(scanners.take(1), scanners.drop(1).toSet())

        normalised.forEach {
            println("${it.name} ${it.beacons[0]}")
        }

        normalised[1].name.shouldBe("scanner 1")
        normalised[1].beacons[0].position.shouldBe(Point3D(-618, -824, -621))
        normalised[1].position.shouldBe(Point3D(68, -1246, -43))

        normalised[3].name.shouldBe("scanner 4")
        normalised[3].position.shouldBe(Point3D(-20, -1133, 1061))

        normalised[4].name.shouldBe("scanner 2")
        normalised[4].beacons[0].position.shouldBe(Point3D(456, -540, 1869))
    }

    test("manhattan for 3d-points") {
        Point3D(1, 2, -3).manhattan(Point3D(-4, 5, 20)).shouldBe(31)
    }

    test("max manhattan") {
        maxScannerManhattan(parseScanners(example)).shouldBe(3621)
    }
})