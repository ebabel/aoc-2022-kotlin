import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    measureTime {
        val testInput = Template(testInput)
        testInput.part1()
            .also { println("Part1 test $it") }
            .also { check(it == "result") }
        testInput.part2()
            .also { println("Part2 test $it") }
            .also { check(it == "result") }

        val realInput = Template(input)
        realInput.part1()
            .also { println("Part1 real $it") }
            .also { check(it == "result") }
        realInput.part2()
            .also { println("Part2 real $it") }
            .also { check(it == "result") }
    }.also {
        println("Took ${it.inWholeSeconds} seconds or ${it.inWholeMilliseconds}ms.")
    }
}

class Template(private val input: String) {

    fun part1(): String {

        return "result"
    }

    fun part2(): String {

        return "result"
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