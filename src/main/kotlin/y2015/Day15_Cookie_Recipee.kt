package y2015

fun main(args: Array<String>) {

    println("Part1 test ${part1(testInput)}")
    println("Part1 real ${part1(input)}")
    println("Part2 test ${part2(testInput)}")
    println("Part2 real ${part2(input)}")
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
        return maxOf(0, ingredients.mapIndexed {index, quantity ->
            quantity * ingredientsLookup[index].capacity
        }.sum()) * maxOf(0, ingredients.mapIndexed {index, quantity ->
            quantity * ingredientsLookup[index].durability
        }.sum()) * maxOf(0, ingredients.mapIndexed {index, quantity ->
            quantity * ingredientsLookup[index].flavor
        }.sum()) * maxOf(0, ingredients.mapIndexed {index, quantity ->
            quantity * ingredientsLookup[index].texture
        }.sum())
    }
    fun ingredients(): Int = ingredients.sum()

}

private fun part1(input: String): String {

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

//    Cookie(listOf(44,56)).ingredients.joinToString(",").also(::println)

    var mockup = List<Int>(ingredientsLookup.size) {1}

    (1..100-ingredientsLookup.size).fold(mockup) { acc, indx ->
        List(ingredientsLookup.size) { index ->
            val newList = acc.toMutableList()
            newList[index]++
            val cookie = Cookie(newList)
            cookie
        }.maxBy { it.score(ingredientsLookup) }.let {
            it.ingredients
        }

    }.also {
        return "${it.joinToString(",")} ${Cookie(it).score(ingredientsLookup)}"
    }


//    buildPermutations(
//        emptyList(),
//        ingredientsLookup
//    )
//        .also{ println("size before filter: ${it.size}") }
//        .filter { cookie -> cookie.ingredients() == 100 }
//        .also{ println("size after filter: ${it.size}") }
////        .forEach { it.ingredients.also(::println) }
//    .maxBy { it.score(ingredientsLookup) }.ingredients.also(::println)


//    Cookie(mapOf(ingredients[0] to 44, ingredients[1] to 56)).score().also(::println)
//    Cookie(mapOf(ingredients[0] to 44, ingredients[1] to 56)).ingredients.values.joinToString { "," }.also(::println)

}

private fun buildPermutations(cookies: List<Cookie>, ingredients: List<Ingredient>): List<Cookie> {
    if (ingredients.isEmpty()) {
        return cookies
    }
    val ingredient = ingredients.first()
//        println("Ingredient: ${ingredient.name} Cookies: ${cookies.size} ingredients: ${ingredients.size}")
    val newIngredientList = ingredients.minus(ingredient)

    return if (cookies.isEmpty()) {
        (0..100).flatMap { secondIterator ->
            buildPermutations(cookies.plus(Cookie(listOf(secondIterator))), newIngredientList)
        }
    } else {
        cookies.flatMap { cookie ->
            (0..100).flatMap { secondIterator ->
                if (cookie.ingredients() + secondIterator <= 100) {
                    val newCookie = Cookie(cookie.ingredients.plus(secondIterator))
                    //Cookie(cookie.ingredients.plus(ingredient to secondIterator))
                    println("newCookie: ${newCookie.ingredients.joinToString(",")}")
                    buildPermutations(cookies.plus(newCookie), newIngredientList)
                } else {
                    emptyList()
                }
            }.filter { cookie ->
                cookie.ingredients() <= 100
            }
        }
    }

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
