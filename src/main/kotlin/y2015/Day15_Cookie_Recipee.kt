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
    val ingredients: Map<Ingredient, Int>
) {
    fun score(): Int {
        return maxOf(0, ingredients.map {
            it.key.capacity * it.value
        }.sum()) * maxOf(0, ingredients.map {
            it.key.durability * it.value
        }.sum()) * maxOf(0, ingredients.map {
            it.key.flavor * it.value
        }.sum()) * maxOf(0, ingredients.map {
            it.key.texture * it.value
        }.sum())
    }
    fun ingredients(): Int = ingredients.values.sum()
}

private fun part1(input: String): String {

    var nameIndex = 0
    val ingredients = input.lines().map { it.split(" ") }.map { line ->
        Ingredient(
            name = nameIndex++,//line.first().dropLast(1),
            capacity = line[2].dropLast(1).toInt(),
            durability = line[4].dropLast(1).toInt(),
            flavor = line[6].dropLast(1).toInt(),
            texture = line[8].dropLast(1).toInt(),
            calories = line.last().toInt(),
        )
    }

    Cookie(mapOf(ingredients[0] to 44, ingredients[1] to 56)).ingredients.values.joinToString(",").also(::println)

    val cookie = mutableMapOf<Ingredient, Int>()

    fun buildPermutations(cookies: List<Cookie>, ingredients: List<Ingredient>): List<Cookie> {
        if (ingredients.isEmpty()) {
            return cookies
        }
        val ingredient = ingredients.first()
        println("Ingredient: ${ingredient.name} Cookies: ${cookies.size} ingredients: ${ingredients.size}")
        val newIngredientList = ingredients.minus(ingredient)

        return if (cookies.isEmpty()) {
            (0..100).flatMap { secondIterator ->
                buildPermutations(cookies.plus(Cookie(mapOf(ingredient to secondIterator))), newIngredientList)
            }
        } else {
            cookies.flatMap { cookie ->
                (0..100).flatMap { secondIterator ->
                    if (cookie.ingredients() + secondIterator <= 100) {
                        val newCookie = Cookie(cookie.ingredients.plus(ingredient to secondIterator))
                        println("newCookie: ${newCookie.ingredients.values.joinToString(",")}")
                        buildPermutations(cookies.plus(newCookie), newIngredientList)
                    } else {
                        emptyList()
                    }
                }.filter { cookie ->
                    cookie.ingredients.map { it.value }.sum() <= 100
                }
            }
        }

    }

    buildPermutations(
        emptyList(),
        ingredients
    )
        .also{ println("size before filter: ${it.size}") }
        .filter { cookie -> cookie.ingredients.map { it.value }.sum() == 100 }
        .also{ println("size after filter: ${it.size}") }
//        .forEach { it.ingredients.also(::println) }
    .maxBy { it.score() }.ingredients.also(::println)


    // 1st 100
    // 1st 99, 2nd 1

//    Cookie(mapOf(ingredients[0] to 44, ingredients[1] to 56)).score().also(::println)
//    Cookie(mapOf(ingredients[0] to 44, ingredients[1] to 56)).ingredients.values.joinToString { "," }.also(::println)

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
