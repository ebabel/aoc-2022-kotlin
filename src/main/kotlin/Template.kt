import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    measureTime {
        val testInput = Template(testInput)
        testInput.part1().expecting(0L)
        testInput.part2().expecting(0L)

        val realInput = Template(input)
        realInput.part1().expecting(0L)
        realInput.part2().expecting(0L)
    }.also {
        println("Took ${it.inWholeSeconds} seconds or ${it.inWholeMilliseconds}ms.")
    }
}

class Template(private val input: String) {

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