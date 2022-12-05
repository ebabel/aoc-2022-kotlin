package y2015

private const val CONWAYS_CONSTANT = 1.303577269034296
fun main(args: Array<String>) {


//    lookAndSay(5, testInput)
//    lookAndSay(40, input)
//    lookAndSay(50, input)

    var i = input.length.toDouble()
    repeat(40) { i *= CONWAYS_CONSTANT }
    println("i $i")
}

private fun lookAndSay(timesToRun: Int, input: String) {
    var previousValue = '0'
    var count = 1
    var working = input
    var current = ""

    (1..timesToRun).forEach { time ->
        working.forEach {
            if (previousValue == '0') {
                previousValue = it
            } else if (it != previousValue) {
                if (count > 9) error("uh oh")
//                println("char $previousValue")
//                current = current * 100 + count * 10 + Character.getNumericValue(previousValue)
                current = "$current$count$previousValue"
                previousValue = it
                count = 1
            } else {
                count++
            }
        }
//        println("$previousValue Character.getNumericValue(previousValue) ${Character.getNumericValue(previousValue)} ")
//        println("char $previousValue")
//        current = current * 100 + count * 10 + Character.getNumericValue(previousValue)
        current = "$current$count$previousValue"
        working = current
        println("$time $working $current ${working.length}")
        current = ""
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
