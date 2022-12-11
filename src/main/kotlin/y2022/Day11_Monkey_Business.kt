package y2022

import alsoPrintOnLines

fun main(args: Array<String>) {

    val testInput = Day11(testInput)
    testInput.part1()
        .also { println("Part1 test $it") }
        .also { check(it == "10605") }
    testInput.part2()
        .also { println("Part2 test $it") }
        .also { check(it == "2713310158") }

    val realInput = Day11(input)
    realInput.part1()
        .also { println("Part1 real $it") }
        .also { check(it == "55930") }
    realInput.part2()
        .also { println("Part2 real $it") }
        .also { check(it == "14636993466") }
}

class Day11(private val input: String) {

    data class Monkey(
        val name: String,
    ) {
        val items: MutableList<Long> = mutableListOf()
        var operation: ((Long) -> Long)? = null
        var test: Int? = 0
        var throwToTrue: Int? = null
        var throwToFalse: Int? = null
        var inspectedCount: Long = 0L
    }

    fun part1(): String {
        val monkeys = parseMonkeys()
        val commonDenominator = monkeys.map { it.test!! }.reduce { acc, i -> acc * i }

        repeat(20) {
            roundOMonkeys(monkeys, commonDenominator, false)
        }

        return monkeys.map { it.name to it.inspectedCount}
            .alsoPrintOnLines()
            .sortedBy { it.second }
            .takeLast(2)
            .fold(1L) { acc, pair ->
                acc * pair.second
            }.toString()
    }

    lateinit var monkey: Monkey
    private fun parseMonkeys(): MutableList<Monkey> {
        val monkeys = mutableListOf<Monkey>()
        input.lines().map { it.trim().split(" ") }.forEach { line ->
            if (line[0] == "Monkey") {
                monkey = Monkey(line[1].dropLast(1))
                monkeys.add(monkey)
            } else if (line[0] == "Starting") {
                monkey.items.addAll(
                    line.drop(2).map { it.replace(",", "") }.map { it.toLong() }
                )
            } else {
                val lastWord = line.last().toIntOrNull() ?: 0
                if (line[0] == "Test:") {
                    monkey.test = lastWord
                } else if (line[0] == "If") {
                    if (line[1] == "true:") {
                        monkey.throwToTrue = lastWord
                    } else {
                        monkey.throwToFalse = lastWord
                    }
                } else if (line[0] == "Operation:") {
                    val (b, c) = line.takeLast(2)
                    monkey.operation = if (b == "*") {
                        if (c == "old") {
                            { x -> x * x }
                        } else {
                            { x -> lastWord * x }
                        }
                    } else {
                        if (c == "old") {
                            { x -> x + x }
                        } else {
                            { x -> lastWord + x }
                        }
                    }

                } else {
                    println("unhandled line: $line")
                }
            }
        }
        return monkeys
    }

    fun part2(): String {

        val monkeys = parseMonkeys()

        val commonDenominator = monkeys.map { it.test!! }.reduce { acc, i ->
            acc * i
        }

        monkeys.alsoPrintOnLines()

        repeat(10000) {
            roundOMonkeys(monkeys, commonDenominator, true)
        }

        return monkeys.map { it.name to it.inspectedCount}
            .sortedBy { it.second }
            .takeLast(2)
            .alsoPrintOnLines()
            .fold(1L) { acc, pair ->
                acc * pair.second
            }.toString()
    }
}

fun roundOMonkeys(monkeys: MutableList<Day11.Monkey>, commonDenominator: Int, isPart2: Boolean) {
    val divider = if (isPart2) {
        1L
    } else {
        3L
    }
    monkeys.forEach { monkey ->
        monkey.inspectedCount += monkey.items.count()
        monkey.items.forEach { originalWorry ->
            var worry = monkey.operation!!.invoke(originalWorry) / divider
            if (isPart2) {
                while (worry > commonDenominator) {
                    worry -= commonDenominator
                }
            }
            if (worry % monkey.test!! == 0L) {
                monkeys[monkey.throwToTrue!!].items.add(worry)
            } else {
                monkeys[monkey.throwToFalse!!].items.add(worry)
            }
        }
        monkey.items.clear()
    }
}

private val testInput =
    """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
""".trimIndent()
private val input =
    """
Monkey 0:
  Starting items: 89, 84, 88, 78, 70
  Operation: new = old * 5
  Test: divisible by 7
    If true: throw to monkey 6
    If false: throw to monkey 7

Monkey 1:
  Starting items: 76, 62, 61, 54, 69, 60, 85
  Operation: new = old + 1
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 6

Monkey 2:
  Starting items: 83, 89, 53
  Operation: new = old + 8
  Test: divisible by 11
    If true: throw to monkey 5
    If false: throw to monkey 3

Monkey 3:
  Starting items: 95, 94, 85, 57
  Operation: new = old + 4
  Test: divisible by 13
    If true: throw to monkey 0
    If false: throw to monkey 1

Monkey 4:
  Starting items: 82, 98
  Operation: new = old + 7
  Test: divisible by 19
    If true: throw to monkey 5
    If false: throw to monkey 2

Monkey 5:
  Starting items: 69
  Operation: new = old + 2
  Test: divisible by 2
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 6:
  Starting items: 82, 70, 58, 87, 59, 99, 92, 65
  Operation: new = old * 11
  Test: divisible by 5
    If true: throw to monkey 7
    If false: throw to monkey 4

Monkey 7:
  Starting items: 91, 53, 96, 98, 68, 82
  Operation: new = old * old
  Test: divisible by 3
    If true: throw to monkey 4
    If false: throw to monkey 2
""".trimIndent()


/**

 */