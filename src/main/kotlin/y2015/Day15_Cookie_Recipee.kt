package y2015

fun main(args: Array<String>) {

    println("Part1 test ${part1(testInput)}")
    println("Part1 real ${part1(input)}")
    println("Part2 test ${part2(testInput)}")
    println("Part2 real ${part2(input)}")
}

private data class Cookie(
    val name: String,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int,
) {
//    fun score(size: Int) =
}

private fun part1(input: String): String {

    val cookies = input.lines().map { it.split(" ") }.map {line ->
        Cookie(
            name = line.first().dropLast(1),
            capacity = line[2].dropLast(1).toInt(),
            durability = line[4].dropLast(1).toInt(),
            flavor = line[6].dropLast(1).toInt(),
            texture = line[8].dropLast(1).toInt(),
            calories = line.last().toInt(),
        )
    }

    return "result"
}

private fun part2(input: String): String {

    return "result"
}


private val testInput =
    """
Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
""".trimIndent()
private val input =
    """
Frosting: capacity 4, durability -2, flavor 0, texture 0, calories 5
Candy: capacity 0, durability 5, flavor -1, texture 0, calories 8
Butterscotch: capacity -1, durability 0, flavor 5, texture 0, calories 6
Sugar: capacity 0, durability 0, flavor -2, texture 2, calories 1
""".trimIndent()
