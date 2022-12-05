package y2015

private const val QUOTE = "\""
private const val BACKSLASH = "\\"
fun main(args: Array<String>) {

    part1(testInput)
//    part1(input)
//    part2(testInput)
//    part2(input)

}

data class Fare (val from: String, val to: String, val time: Int, val trips: Int = 0)

private fun part1(testInput1: String) {
    val mutableMap = mutableMapOf<String, Int>()
    val list = mutableListOf<Fare>()
    val destinations = mutableListOf<String>()
    var tempStart = ""

    testInput1.lines().map { line ->
        val a = line.split(" ")
        if (!destinations.contains(a[0])) {
            destinations.add(a[0])
        }
        if (!destinations.contains(a[2])) {
            destinations.add(a[2])
        }
        val fare = Fare(a[0], a[2], a[4].toInt())
        val reverseFare = Fare(a[2], a[0], a[4].toInt())
        list.add(fare)
        list.add(reverseFare)

        val newFares = list.mapNotNull {
//            println()
            if (fare.from == it.to && it.from != fare.to) {
                Fare(it.from, fare.to, it.time+fare.time, it.trips+1)
            } else null
        }
        list.addAll(newFares)

    }
    list
        .filter { it.trips == (destinations.size - 2) }
        .sortedBy { it.time }
        .forEach { println(it) }
    destinations.size.also(::println)
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
Faerun to AlphaCentauri = 13
Faerun to Arbre = 24
Faerun to Snowdin = 60
Faerun to Tambi = 71
Faerun to Straylight = 67
Norrath to Tristram = 142
Norrath to AlphaCentauri = 15
Norrath to Arbre = 135
Norrath to Snowdin = 75
Norrath to Tambi = 82
Norrath to Straylight = 54
Tristram to AlphaCentauri = 118
Tristram to Arbre = 122
Tristram to Snowdin = 103
Tristram to Tambi = 49
Tristram to Straylight = 97
AlphaCentauri to Arbre = 116
AlphaCentauri to Snowdin = 12
AlphaCentauri to Tambi = 18
AlphaCentauri to Straylight = 91
Arbre to Snowdin = 129
Arbre to Tambi = 53
Arbre to Straylight = 40
Snowdin to Tambi = 15
Snowdin to Straylight = 99
Tambi to Straylight = 70
""".trimIndent()
