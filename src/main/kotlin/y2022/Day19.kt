package y2022

import expecting
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    measureTime {
        val testInput = Day19(testInput)
        testInput.part1().expecting(0L)
        testInput.part2().expecting(0L)

        val realInput = Day19(input)
        realInput.part1().expecting(0L)
        realInput.part2().expecting(0L)
    }.also {
        println("Took ${it.inWholeSeconds} seconds or ${it.inWholeMilliseconds}ms.")
    }
}

class Day19(private val input: String) {

    fun part1(): Long {

        return -1
    }

    fun part2(): Long {

        return -1
    }
}


private val testInput =
"""

""".trimIndent()
private val input =
"""

""".trimIndent()


/**

 */