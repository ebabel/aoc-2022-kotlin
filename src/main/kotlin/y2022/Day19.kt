package y2022

import alsoPrintOnLines
import expecting
import kotlin.math.ceil
import kotlin.math.roundToInt
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    measureTime {
        val testInput = Day19(testInput)
        testInput.part1().expecting(-1L)
//        testInput.part2().expecting(-1L)
//
        val realInput = Day19(input)
//        realInput.part1().expecting(-1L)
//        realInput.part2().expecting(-1L)
    }.also {
        println("Took ${it.inWholeSeconds} seconds or ${it.inWholeMilliseconds}ms.")
    }
}

class Day19(private val input: String) {


    enum class Resource(val named: String) {
        ORE("ore"),
        CLAY("clay"),
        OBSIDIAN("obsidian"),
        GEODE("geode")
    }

    data class Blueprint(
        val name: String,
        val robotRecipes: List<Robot>,

    )
    data class Robot(
        val gathers: Resource,
        val cost: Map<Resource, Int>,
    )
    fun part1(): Long {
        val blueprints = input.lines().map { it.split(" ".replace(".",""))}.mapIndexed { index, strings ->
//            println("$index $strings")
            val robots = mutableListOf<Robot>()
            robots.add(Robot(Resource.ORE, mapOf(Resource.ORE to strings[6].toInt())))
            robots.add(Robot(Resource.CLAY, mapOf(Resource.ORE to strings[12].toInt())))
            robots.add(Robot(Resource.OBSIDIAN, mapOf(Resource.ORE to strings[18].toInt(), Resource.CLAY to strings[21].toInt())))
            robots.add(Robot(Resource.GEODE, mapOf(Resource.ORE to strings[27].toInt(), Resource.OBSIDIAN to strings[30].toInt())))
            Blueprint(
                strings[1],
                robots.toList().reversed()
            )

        }.alsoPrintOnLines()

        return blueprints.mapIndexed { index, blueprint ->
            var maxGeodeEndings = -1
            fun doMinute(robots: List<Robot>, minute: Int, resources: Map<Resource,Int>) {
                val newMinute = minute + 1




                if (newMinute >= 23 && robots.count { it.gathers == Resource.GEODE } < 1) {
                    return
                }
                if (newMinute >= 22 && robots.count { it.gathers == Resource.OBSIDIAN } < 1) {
                    return
                }
                if (newMinute >= 21 && robots.count { it.gathers == Resource.CLAY } < 1) {
                    return
                }
                if (newMinute != 24) {
                    val newResources = resources.toMutableMap()
                    robots.forEach {
                        newResources[it.gathers] = (newResources[it.gathers] ?: 0) + 1
                    }
                    doMinute(robots, newMinute, newResources)
                    blueprint.robotRecipes.forEach {robotRecipe ->
                        robotRecipe.cost.all { (t, u) ->
                            (resources[t]?:0) >= u
                        }.also {
                            if (it) {
//                                if (robotRecipe.gathers == Resource.GEODE) {
////                                    println("wtf")
//                                }
                                val afterSpend = resources.map {(r,q) ->
                                    r to q - (robotRecipe.cost[r] ?: 0)
                                }.toMap().toMutableMap()

                                robots.forEach {
                                    afterSpend[it.gathers] = (afterSpend[it.gathers] ?: 0) + 1
                                }
                                doMinute(robots.plus(robotRecipe), newMinute, afterSpend)
                            }
                        }

                    }
                } else {
                    val newResources = resources.toMutableMap()
                    robots.forEach {
                        newResources[it.gathers] = (newResources[it.gathers] ?: 0) + 1
                    }
                    val geodes = newResources[Resource.GEODE] ?: 0
//                    if (geodes > 12) {
//                        println("how did I get here?!")
//                    }
                    if (geodes > maxGeodeEndings) {
                        maxGeodeEndings = geodes
                        println("geodes $geodes")
                    }
                }

            }
//            doMinute(listOf(blueprint.robotRecipes.first { it.gathers == Resource.ORE }), 0, emptyMap())


            fun go(robotToBuyNext: Resource, robots: List<Resource>, resources: List<Resource>, minutes: Int) {
                if (minutes >= 24) {

                    val geodes = (resources.count { it == Resource.GEODE })
                    if (geodes > maxGeodeEndings) {
                        maxGeodeEndings = geodes
                        println("geodes $geodes")
                    }
                    return
                }

                var newMinutes = minutes
                val newResources = resources.toMutableList()
                val resourcesNeeded = blueprint.robotRecipes.first { it.gathers == robotToBuyNext }
                while (newMinutes <= 24) {
                    newMinutes++
                    val canBuy = resourcesNeeded.cost.all {(resource, quant) ->
                        newResources.count { it == resource } >= quant
                    }
                    if (canBuy) {
//                        println("bought $robotToBuyNext")
                        resourcesNeeded.cost.forEach { (resource, quant) ->
                            repeat(quant) { newResources.remove(resource) }
                        }

                        newResources.addAll(robots)
                        Resource.values().forEach {
                            go(it, robots.plus(robotToBuyNext), newResources, newMinutes)
                        }
                        break
                    } else {

//                        println("cost $resourcesNeeded $robots")
                        val hasMeans = resourcesNeeded.cost.map { (t, cost) ->
                            robots.count { it == t } > 0
                        }.all { it }
//                        println("Has means? $hasMeans")
                        if (!hasMeans) {
                            break
                        }
                        val minsToSkip = resourcesNeeded.cost.map { (t, cost) ->
                            ceil((cost-newResources.count { it == t }) / (robots.count { it == t }.toDouble()))
                        }.max().toInt()
//                        println("minutes to skip $minsToSkip")
                        if (minsToSkip == 1) {
                            newResources.addAll(robots)
                        } else if (minsToSkip + newMinutes > 24) {
                            break
                        } else {
                            repeat(minsToSkip-1) {
                                newMinutes++
                                newResources.addAll(robots)
                            }
                            newResources.addAll(robots)
                        }
//                    } else {
                    }


                }


                while (newMinutes <= 24) {
                    newMinutes++
                    newResources.addAll(robots)
                }

                val geodes = (newResources.count { it == Resource.GEODE })
                if (geodes > maxGeodeEndings) {
                    maxGeodeEndings = geodes
                    println("geodes $geodes")
                }


            }

            Resource.values().forEach {
                go(it, listOf(Resource.ORE), emptyList(), 1)
            }
            println("${blueprint.name} $maxGeodeEndings")
            ((index+1) * maxGeodeEndings).toLong()
        }.sum()
    }

    fun part2(): Long {

        return -1
    }
}


private val testInput =
"""
Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
""".trimIndent()
private val input =
"""
Blueprint 1: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 19 clay. Each geode robot costs 3 ore and 17 obsidian.
Blueprint 2: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 17 clay. Each geode robot costs 3 ore and 8 obsidian.
Blueprint 3: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 16 clay. Each geode robot costs 2 ore and 11 obsidian.
Blueprint 4: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 14 clay. Each geode robot costs 4 ore and 15 obsidian.
Blueprint 5: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 17 clay. Each geode robot costs 3 ore and 16 obsidian.
Blueprint 6: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 7 clay. Each geode robot costs 2 ore and 7 obsidian.
Blueprint 7: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 4 ore and 19 clay. Each geode robot costs 4 ore and 12 obsidian.
Blueprint 8: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 10 clay. Each geode robot costs 3 ore and 14 obsidian.
Blueprint 9: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 8 clay. Each geode robot costs 3 ore and 19 obsidian.
Blueprint 10: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 14 clay. Each geode robot costs 3 ore and 20 obsidian.
Blueprint 11: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 11 clay. Each geode robot costs 3 ore and 14 obsidian.
Blueprint 12: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 6 clay. Each geode robot costs 4 ore and 11 obsidian.
Blueprint 13: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 4 ore and 20 clay. Each geode robot costs 4 ore and 8 obsidian.
Blueprint 14: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 19 clay. Each geode robot costs 3 ore and 8 obsidian.
Blueprint 15: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 13 clay. Each geode robot costs 3 ore and 11 obsidian.
Blueprint 16: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 17 clay. Each geode robot costs 3 ore and 11 obsidian.
Blueprint 17: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 7 clay. Each geode robot costs 3 ore and 8 obsidian.
Blueprint 18: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 15 clay. Each geode robot costs 2 ore and 15 obsidian.
Blueprint 19: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 18 clay. Each geode robot costs 4 ore and 16 obsidian.
Blueprint 20: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 5 clay. Each geode robot costs 3 ore and 18 obsidian.
Blueprint 21: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 4 ore and 6 clay. Each geode robot costs 3 ore and 11 obsidian.
Blueprint 22: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 19 clay. Each geode robot costs 4 ore and 15 obsidian.
Blueprint 23: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 17 clay. Each geode robot costs 3 ore and 19 obsidian.
Blueprint 24: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 18 clay. Each geode robot costs 4 ore and 8 obsidian.
Blueprint 25: Each ore robot costs 3 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 17 clay. Each geode robot costs 4 ore and 16 obsidian.
Blueprint 26: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 3 ore and 17 clay. Each geode robot costs 4 ore and 20 obsidian.
Blueprint 27: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 10 clay. Each geode robot costs 3 ore and 10 obsidian.
Blueprint 28: Each ore robot costs 4 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 10 clay. Each geode robot costs 4 ore and 10 obsidian.
Blueprint 29: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 14 clay. Each geode robot costs 3 ore and 8 obsidian.
Blueprint 30: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 11 clay. Each geode robot costs 4 ore and 8 obsidian.
""".trimIndent()


/**

 */