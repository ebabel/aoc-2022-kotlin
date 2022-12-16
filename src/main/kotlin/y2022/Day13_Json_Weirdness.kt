package y2022

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive

fun main(args: Array<String>) {

    val testInput = Day13(testInput)
    testInput.part1()
        .also { println("Part1 test $it") }
        .also { check(it == "result") }
//    testInput.part2()
//        .also { println("Part2 test $it") }
//        .also { check(it == "result") }
//
//    val realInput = Day13(input)
//    realInput.part1()
//        .also { println("Part1 real $it") }
//        .also { check(it == "result") }
//    realInput.part2()
//        .also { println("Part2 real $it") }
//        .also { check(it == "result") }
}
fun JsonElement.compareWith(other: JsonElement): Boolean {
//    println("this $this other $other")
    when (this) {
        is JsonObject -> {
            error("are there objects?")
        }
        is JsonArray -> {
            return mapIndexed { index, jsonElement ->
                if (other is JsonPrimitive) {
//                    println("other is primi $other")
                    jsonElement.compareWith(other)
                } else {
                    val otherArray = (other as JsonArray)
                    if (index !in otherArray.indices) {
                        true
                    } else {
                        jsonElement.compareWith(otherArray[index])
                    }
                }
            }.any { it }
        }
        is JsonPrimitive ->  {
//            println("primi $other")
            if (other is JsonPrimitive) {
                val otherInt = (other as JsonPrimitive).int
//                println("$int > $otherInt")
                return int < otherInt
            } else {
                val otherInt = (other as JsonArray)[0].jsonPrimitive.int
//                println("$int > $otherInt")
                return int < otherInt
            }
        }
        else -> println("else")
    }
    error("return?")
}
class Day13(private val input: String) {

    fun part1(): String {

        var firstWorking = ""
        var workingPair = 1
        input.lines().forEachIndexed { index, s ->
            if (s.isEmpty()){

            } else if (firstWorking.isEmpty()) {
                firstWorking = s
            } else {

                val result = Json.parseToJsonElement(firstWorking).compareWith(Json.parseToJsonElement(s))

                println("${workingPair++} $result")

                firstWorking = ""
            }
        }
        return "result"
    }

    fun part2(): String {

        return "result"
    }
}



private val testInput =
"""
[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]
""".trimIndent()
private val input =
"""
    
""".trimIndent()


/**

 */