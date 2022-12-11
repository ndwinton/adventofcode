package aoc2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2211Spec : FunSpec({

    val monkeyText = """
        |Monkey 0:
        |  Starting items: 79, 98
        |  Operation: new = old * 19
        |  Test: divisible by 23
        |    If true: throw to monkey 2
        |    If false: throw to monkey 3
    """.trimMargin()

    val example = """
        |Monkey 0:
        |  Starting items: 79, 98
        |  Operation: new = old * 19
        |  Test: divisible by 23
        |    If true: throw to monkey 2
        |    If false: throw to monkey 3
        |
        |Monkey 1:
        |  Starting items: 54, 65, 75, 74
        |  Operation: new = old + 6
        |  Test: divisible by 19
        |    If true: throw to monkey 2
        |    If false: throw to monkey 0
        |
        |Monkey 2:
        |  Starting items: 79, 60, 97
        |  Operation: new = old * old
        |  Test: divisible by 13
        |    If true: throw to monkey 1
        |    If false: throw to monkey 3
        |
        |Monkey 3:
        |  Starting items: 74
        |  Operation: new = old + 3
        |  Test: divisible by 17
        |    If true: throw to monkey 0
        |    If false: throw to monkey 1
    """.trimMargin()
    test("parse monkey") {
        val monkey = parseMonkeyText(monkeyText)
        monkey.id.shouldBe(0)
        monkey.items.shouldBe(mutableListOf(79, 98))
        monkey.op.invoke(20).shouldBe(380)
        monkey.divisor.shouldBe(23)
        monkey.onTrue.shouldBe(2)
        monkey.onFalse.shouldBe(3)
    }

    test("parsing all") {
        val monkeys = parseAllMonkeys(example)
        monkeys.size.shouldBe(4)
        monkeys[0].op.invoke(2).shouldBe(38)
        monkeys[1].op.invoke(2).shouldBe(8)
        monkeys[2].op.invoke(2).shouldBe(4)
        monkeys[3].op.invoke(2).shouldBe(5)
    }

    test("just do a round") {
        val monkeys = parseAllMonkeys(example)
        runRound(monkeys)
        monkeys.forEach { println(it) }
    }

    test("monkey business") {
        monkeyBusiness(example).shouldBe(10605L)
    }

    test("monkey business part 2") {
        monkeyBusiness(example, 10000, 1).shouldBe(2713310158L)
    }
})