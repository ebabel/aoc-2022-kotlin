package y2015

private const val CONWAYS_CONSTANT = 1.303577269034296
fun main(args: Array<String>) {
    lookAndSay(50, input)
}

private fun lookAndSay(timesToRun: Int, input: String) {
    var previousValue = '0'
    var count = 1
    var working = input
    var current = mutableListOf<String>()

    (1..timesToRun).forEach { time ->
        working.forEach {
            if (previousValue == '0') {
                previousValue = it
            } else if (it != previousValue) {
                if (count > 9) error("uh oh")
                current.add("$count$previousValue")
                previousValue = it
                count = 1
            } else {
                count++
            }
        }
        current.add("$count$previousValue")
        working = current.joinToString("")
        if (time == 40 || time == 50) {
            println("$time  ${working.length}")
        }
        current.clear()
        previousValue = '0'
        count = 1
    }
}

private val testInput =
"""
1
""".trimIndent()
private val input =
"""
3113322113
""".trimIndent()
