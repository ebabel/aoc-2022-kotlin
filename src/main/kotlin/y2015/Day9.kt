package y2015

private const val QUOTE = "\""
private const val BACKSLASH = "\\"
fun main(args: Array<String>) {

    part1(testInput)
    part1(input)
//    part2(testInput)
//    part2(input)

}

data class Fare (val from: String, val to: String, val time: Int, val destinations: List<String>)


val solutionList = mutableListOf<Int>()
private fun part1(testInput1: String) {
    solutionList.clear()
    val mutableMap = mutableMapOf<String, Int>()
    val destinations = mutableSetOf<String>()
    testInput1.lines().map { line ->
        val a = line.split(" ")
        if (mutableMap.containsKey("${a[0]}, ${a[2]}")) error("")
        if (mutableMap.containsKey("${a[2]}, ${a[0]}")) error("")
        mutableMap["${a[0]}, ${a[2]}"] = a[4].toInt()
        mutableMap["${a[2]}, ${a[0]}"] = a[4].toInt()
        destinations.add(a[0])
        destinations.add(a[2])
    }

    fun buildTrip(destinations: List<String>, tripSoFar: List<String>, time: Int = 0) {
        if (destinations.isEmpty()) {
//            println("$tripSoFar $time")
            solutionList.add(time)
        }
        destinations.forEach {
            if (tripSoFar.isEmpty()) {
                buildTrip(destinations.minus(it), tripSoFar.plus(it), time)
            } else {
                val nextTime = mutableMap["${tripSoFar.last()}, $it"]
                if (nextTime != null) {
                    buildTrip(destinations.minus(it), tripSoFar.plus(it), time + nextTime)
                }
            }
        }
    }

    buildTrip(destinations.toList(), emptyList())
    println("buildTrip part1 ${solutionList.min()}")
    println("buildTrip part2 ${solutionList.max()}")

}


private fun part2(testInput1: String) {
    testInput1.lines().map { line ->

    }
}

private val testInput =
    """
London to Dublin = 464
London to Belfast = 518
Dublin to Belfast = 141
""".trimIndent()
private val input =
"""
Faerun to Norrath = 129
Faerun to Tristram = 58
Faerun to AlphaCentauri = 13   // 1
Faerun to Arbre = 24
Faerun to Snowdin = 60
Faerun to Tambi = 71
Faerun to Straylight = 67
Norrath to Tristram = 142
Norrath to AlphaCentauri = 15  // 2
Norrath to Arbre = 135
Norrath to Snowdin = 75  // 3
Norrath to Tambi = 82
Norrath to Straylight = 54
Tristram to AlphaCentauri = 118
Tristram to Arbre = 122
Tristram to Snowdin = 103  // 4
Tristram to Tambi = 49 // 5
Tristram to Straylight = 97
AlphaCentauri to Arbre = 116
AlphaCentauri to Snowdin = 12
AlphaCentauri to Tambi = 18
AlphaCentauri to Straylight = 91
Arbre to Snowdin = 129
Arbre to Tambi = 53 // 6
Arbre to Straylight = 40 // 7
Snowdin to Tambi = 15
Snowdin to Straylight = 99
Tambi to Straylight = 70
""".trimIndent()
