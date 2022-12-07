
fun main(args: Array<String>) {
    val testInput = Template(testInput)
    println("Part1 test ${testInput.part1().also { check(it == "result") }}")
    println("Part2 test ${testInput.part2().also { check(it == "result") }}")

    val realInput = Template(input)
    println("Part1 real ${realInput.part1().also { check(it == "result") }}")
    println("Part2 real ${realInput.part2().also { check(it == "result") }}")

}

class Template(val input: String) {

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