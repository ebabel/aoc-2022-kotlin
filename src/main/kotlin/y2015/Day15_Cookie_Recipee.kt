package y2015

fun main(args: Array<String>) {



    println("Part1 test ${part1(testInput)}")
    println("Part1 real ${part1(input)}")
    println("Part2 test ${part2test(testInput)}")
    println("Part2 real ${part2real(input).also { check(it.split(" ").last().toInt() == 15862900) }}")
}

private fun ingredientsLookupBuilder(input: String): List<Ingredient> {
    var nameIndex = 0
    val ingredientsLookup = input.lines().map { it.split(" ") }.map { line ->
        Ingredient(
            name = nameIndex++,//line.first().dropLast(1),
            capacity = line[2].dropLast(1).toInt(),
            durability = line[4].dropLast(1).toInt(),
            flavor = line[6].dropLast(1).toInt(),
            texture = line[8].dropLast(1).toInt(),
            calories = line.last().toInt(),
        )
    }
    return ingredientsLookup
}

private data class Ingredient(
    val name: Int,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int,
)

private data class Cookie(
    val ingredients: List<Int> // indexed by ingredient ie Butterscotch is 0, Cinnamon is 1
) {
    fun score(ingredientsLookup: List<Ingredient>): Int {
        return maxOf(0, ingredients.mapIndexed { index, quantity ->
            quantity * ingredientsLookup[index].capacity
        }.sum()) * maxOf(0, ingredients.mapIndexed { index, quantity ->
            quantity * ingredientsLookup[index].durability
        }.sum()) * maxOf(0, ingredients.mapIndexed { index, quantity ->
            quantity * ingredientsLookup[index].flavor
        }.sum()) * maxOf(0, ingredients.mapIndexed { index, quantity ->
            quantity * ingredientsLookup[index].texture
        }.sum())
    }

    fun ingredients(): Int = ingredients.sum()
    fun calories(ingredientsLookup: List<Ingredient>): Int =
        ingredients.mapIndexed { index, quantity ->
            quantity * ingredientsLookup[index].calories
        }.sum()

}

private fun part1(input: String): String {
    val ingredientsLookup = ingredientsLookupBuilder(input)
    val firstTrial = List(ingredientsLookup.size) { 1 }
    (1..100 - ingredientsLookup.size).fold(firstTrial) { acc, indx ->
        List(ingredientsLookup.size) { index ->
            val newList = acc.toMutableList()
            newList[index]++
            val cookie = Cookie(newList)
//            println(
//                "${cookie.ingredients.joinToString(",")} ${cookie.score(ingredientsLookup)} ${
//                    cookie.calories(
//                        ingredientsLookup
//                    )
//                }"
//            )
            cookie
        }.maxBy { it.score(ingredientsLookup) }.ingredients
    }.let {
        return "${it.joinToString(",")} ${Cookie(it).score(ingredientsLookup)}"
    }
}

private fun part2test(input: String): String {
    val ingredientsLookup = ingredientsLookupBuilder(input)
    var workingCookie = Cookie(listOf(1, 1))
    (0..100).forEach { a ->
        (0..100).forEach { b ->
            if (a + b == 100) {
                val cookie = Cookie(listOf(a, b))
//                println("${cookie.ingredients.joinToString(",")} ${cookie.score(ingredientsLookup)}")
                if (cookie.calories(ingredientsLookup) <= 500) {
                    workingCookie = listOf(cookie, workingCookie).maxBy { it.score(ingredientsLookup) }
//                    println("${workingCookie.ingredients.joinToString(",")} ${workingCookie.score(ingredientsLookup)}")
                }
            }
        }
    }
    return "${workingCookie.ingredients.joinToString(",")} ${workingCookie.score(ingredientsLookup)}"
}

private fun part2real(input: String): String {
    val ingredientsLookup = ingredientsLookupBuilder(input)
    var workingCookie = Cookie(ingredientsLookup.map { 1 })
    permutations(4,1..100) { permutation ->
        if (permutation.sum() == 100) {
            val cookie = Cookie(permutation.reversed())
            if (cookie.calories(ingredientsLookup) <= 500) {
                workingCookie = listOf(cookie, workingCookie).maxBy { it.score(ingredientsLookup) }
            }
        }
    }

    return "${workingCookie.ingredients.joinToString(",")} ${workingCookie.score(ingredientsLookup)}".also(::println)
}

// This permutation table only calls operation() 4^4=256 times.
private fun permutations(itemSpaces: Int, itemRange: IntRange, preventDupes: Boolean = false, operation: (List<Int>) -> Unit) {
    val mutableList = MutableList(itemSpaces) {itemRange.first}
    fun increment(workingIndex: Int): Boolean {
        if (workingIndex == itemSpaces) {
            return true
        }
        mutableList[workingIndex]++
        if (mutableList[workingIndex] > itemRange.last) {
            mutableList[workingIndex] = 0
            return increment(workingIndex+1)
        }
        return false
    }
    while (true) {
        if (!preventDupes || (mutableList.toSet().size == mutableList.size))
        operation(mutableList)
        val isDone = increment(0)
        if (isDone) {
            break
        }
    }

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
