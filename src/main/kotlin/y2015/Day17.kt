package y2015

import alsoPrintOnLines
import permutationSet

fun main(args: Array<String>) {
    val testInput = Day17(testInput)
    testInput.part1(25)
        .also { println("Part1 test $it") }
        .also { check(it == "4") }
    testInput.part2()
        .also { println("Part2 test $it") }
        .also { check(it == "3") }

    val realInput = Day17(input)
    realInput.part1(150)
        .also { println("Part1 real $it") }
        .also { check(it == "4372") }
    realInput.part2()
        .also { println("Part2 real $it") }
        .also { check(it == "4") }

}

class Day17(val input: String) {

    lateinit var filter: List<List<Int>>
    fun part1(amountToStore: Int): String {
        val containers = input.lines().map { it.toInt() }.also { println(it.size) }
        filter = permutationSet(containers.size)
            .map { it.map { containers[it - 1] } }
            .filter { it.sum() == amountToStore }
        return filter
            .count().toString()
    }

    fun part2(): String {

        return filter.minBy { it.count() }
            .count().let { count ->
                filter.count { it.count() == count }
            }.toString()
    }
}


private val testInput =
    """
20
15
10
5
5
""".trimIndent()
private val input =
    """
11
30
47
31
32
36
3
1
5
3
32
36
15
11
46
26
28
1
19
3
""".trimIndent()


/**

 */