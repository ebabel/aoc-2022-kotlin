package y2022

import expecting
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {


    measureTime {
        val testInput = Day17(testInput, TimeSource.Monotonic.markNow())
//        testInput.part1()
//            .also { println("Part1 test $it") }
//            .also { check(it == "3068") {"should be 3068"} }
//        testInput.part2()
//            .also { println("Part2 test $it") }
//            .also { check(it == "result") }
//
        val realInput = Day17(input, TimeSource.Monotonic.markNow())
//        realInput.part(2022L)
//            .also { println("Part1 real $it") }
//            .also { check(it == "3161") }
        realInput.part(1000000000000L)
            .expecting(1575931232076L)
    }.also {
        println("Took ${it.inWholeSeconds} seconds or ${it.inWholeMilliseconds}ms.")
    }
}

class Day17 @OptIn(ExperimentalTime::class) constructor(
    private val input: String,
    private val markNow: TimeSource.Monotonic.ValueTimeMark
) {

    fun debug(log: String) {
        println(log)
    }

    data class Snapshot(
        val top8: List<List<Boolean>>,
        val windPointer: Int,
        val shapePointer: Int,
    )

    data class SnapshotValue (
        val rocks: Long,
        val floors: Int,
    )

    @OptIn(ExperimentalTime::class)
    fun part(rocksToTest: Long): Long {

        val rocks = rocks.split("\n\n").map {
            it.lines().map {
                it.map { it == '#' }
            }.reversed()
        }

        fun List<List<Boolean>>.rockWidth() = this.maxOf { it.size }
        fun List<List<Boolean>>.rockHeight() = this.size

        val towerFloors = mutableListOf<MutableList<Boolean>>()

        var workingWind = 0L
        var workingRockShapeIndex = 0L
        var workingRockX = 2
        var workingRockY = 3
        var rockShape = rocks[0]

        fun canGoLeft(): Boolean {
            if (towerFloors.size <= workingRockY) {
                return workingRockX > 0
            } else if (towerFloors.isNotEmpty()) {
                if (workingRockX <= 0) {
                    return false
                }

                return rockShape.mapIndexed { indexY, rockShapeLine ->
                    rockShapeLine.mapIndexed { indexX, c ->
//                        debug("checking $indexX $indexY $c ")
                        if (towerFloors.size > workingRockY + indexY) {
                            (c && towerFloors[workingRockY + indexY][indexX + workingRockX - 1])
                        } else {
                            false
                        }
                    }.any { it }
                }.none { it }

            }
            return false
        }

        fun canGoRight(): Boolean {
            if (towerFloors.size <= workingRockY) {
                return workingRockX + rockShape.rockWidth() < 7
            } else if (towerFloors.isNotEmpty()) {
                if (workingRockX + rockShape.rockWidth() >= 7) {
                    return false
                }

                return rockShape.mapIndexed { indexY, rockShapeLine ->
                    rockShapeLine.mapIndexed { indexX, c ->
                        if (towerFloors.size > workingRockY + indexY) {
                            (c && towerFloors[workingRockY + indexY][indexX + workingRockX + 1])
                        } else {
                            false
                        }
                    }.any { it }
                }.none { it }

            }
            return false
        }

        fun canDrop(): Boolean {
            if (workingRockY == 0) {
                return false
            } else if (workingRockY > towerFloors.size) {
                return true
            } else if (towerFloors.isNotEmpty()) {

                rockShape.mapIndexed { indexY, s ->
                    if (towerFloors.size >= workingRockY + indexY) {
                        s.mapIndexed { indexX, c ->
                                (c && towerFloors[workingRockY + indexY - 1][indexX + workingRockX])//.also { debug("canDrop check $it") }
                        }.any { it }//.also { debug("canDrop2 returning $it") }
                    } else {
                        false
                    }
                }.none { it }.also {
                    return (it)
                }


            }
            return false
        }

        fun convertToRest() {
            if (workingRockY == towerFloors.size) {
                rockShape
                    .map {
                        val start = (0.until(workingRockX)).map { false }.plus(it)
                        start.plus(start.size.until(7).map { false })
                    }.forEach {
                        towerFloors.add(it.toMutableList())
                    }
            } else {
                rockShape
                    .map {
                        val start = (0.until(workingRockX)).map { false }.plus(it)
                        start.plus(start.size.until(7).map { false })
                    }.forEachIndexed { index, rockLine ->
                        if (workingRockY + index <= towerFloors.size - 1) {
                            val existing = towerFloors[workingRockY + index].toMutableList()
                            towerFloors[workingRockY + index].clear()
                            towerFloors[workingRockY + index].addAll(existing.mapIndexed { index, b ->
                                b || rockLine[index]
                            })
                        } else {
                            towerFloors.add(rockLine.toMutableList())
                        }
                    }
            }

            workingRockShapeIndex++
            workingRockX = 2
            workingRockY = towerFloors.size + 3
        }

        fun tetris() {

            rockShape = rocks[(workingRockShapeIndex % rocks.size).toInt()]
            val windLeft = input[(workingWind++ % input.length).toInt()] == '<'
            if (windLeft && canGoLeft()) workingRockX--
            if (!windLeft && canGoRight()) workingRockX++
            if (canDrop()) {
                workingRockY--
            } else {
                convertToRest()
            }
        }

        fun debug(step: Int) {
            debug("")
            debug("===Step $step===")

            rockShape
                .map {
                    val start = (0.until(workingRockX)).map { false }.plus(it)
                    start.plus(start.size.until(7).map { false })
                }.withIndex().forEach { (index, booleans) ->
                    booleans.map {
                        if (it) '@' else ' '
                    }.also { debug("$it ${index + workingRockY}") }
                }

            debug("floors: ${towerFloors.size}")
            towerFloors.withIndex().reversed().forEach { (index, booleans) ->
                booleans.map {
                    if (it) '#' else ' '
                }.also { debug("$it $index") }
            }
        }

        var skippedFloors = 0L
        var skippedRocks = 0L

        var skipSearchingForSkips = false
        val snapshotMap = mutableMapOf<Snapshot, SnapshotValue>()
        while (workingRockShapeIndex < rocksToTest - skippedRocks) {
            if (workingRockShapeIndex % 100_000L == 0L) {
                val percentage = (workingRockShapeIndex+skippedRocks) / rocksToTest.toDouble() * 100.0
                val estimated = 1/percentage * markNow.elapsedNow().inWholeSeconds
                println("$percentage% estimated:$estimated sec")
            }

            tetris()

            if (towerFloors.size > 9 && !skipSearchingForSkips) {
                val snapshot = Snapshot(towerFloors.takeLast(9), (workingWind % input.length).toInt(), (workingRockShapeIndex % rocks.size).toInt())
                snapshotMap[snapshot]?.let {
                    val timesForLoop = (rocksToTest - workingRockShapeIndex) / (workingRockShapeIndex-it.rocks)

                    skippedRocks = (workingRockShapeIndex-it.rocks) * timesForLoop
                    skippedFloors = (towerFloors.size-it.floors) * timesForLoop
                    skipSearchingForSkips = true
                }
                snapshotMap[snapshot] = SnapshotValue(
                    rocks = workingRockShapeIndex,
                    floors = towerFloors.size,
                )
            }

        }
        return towerFloors.size + skippedFloors
    }

}


private val rocks = """
####

.#.
###
.#.

..#
..#
###

#
#
#
#

##
##
""".trimIndent()
private val testInput =
    """
>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
""".trimIndent()
private val input =
    """
>><>><<<<><<<<><<<>><>>><>>>><>>>><>><<>>><<<>>><<>><>>><<>>>><<<<>>><<<>>>><<>>>><<<<>>><<<>><<<<><>><<>><<>><<>>><<<>><<<>>>><<>>>><<<<>>><<<>><>><<<>><<<<>>>><>>>><<>>><<>>><<>>><<><>>>><<<<><<<<>>><>>><<<<>>>><>>>><><<>><<>><<<<><<<<>>><<<<>>><<>>><<<><<<>><<<>><><<>>>><<<<><<<>><<<<>>><>>><<>><<>>><>>>><<<<>>>><<<<>>><<<<>>>><<<<>>>><>><<<<><<<<>><<>>><<>>>><<<>>>><<>>><<<<><<<><>><>><<<<>>><<<>><<<>><<<<>><<>>><<>><<>>><<<<>>>><>><<<<>>>><<<<>>><>>><<<<>>><<>>>><<>>><<><>>><>><<<<><<<<><<<>>><<<<>>><<<<>>>><>>>><<<>>>><>>>><<<<>><>><<<<><>><<<>>><<<<>><<<<>>>><<<>>><<<<>>>><<<><<><<<><<><<<<>><<>>>><<>>>><<<<>><>>><<<<>>>><<<><<<<>>>><<<<>>><<>>>><<>>>><<<><<>><<<>>><<<<>>><><>>>><>><<<<>>>><<<<>>>><>><<><<>>><>><>><<<>>>><<<>>>><<>>><<<<>>><<>><>>>><<<<>>><<<<>><>><><>><>><><<>><<>>>><<<>>>><<<>>><<<><<<><<<<><<<>><>><<>>><<<>><<><<<<>><<<<>><<<>>>><<>><<<><><<><<>>><<<<>>>><<><>><<<<><<>>>><<<><<>><<>>><<<>><<>><<>><<<<>>><<<>><<>><<<>><<<>>><<<>>><<<><<<<>><>>><<>><<<>>>><<>><<>><>><<<<><<>>><>><<>>>><<<>>>><<<><<>>><<<<><<<<>><><<<>>><<>>>><<<>><<<<>><>><<<><<<>>>><<<<>>><>>>><<>>>><<<><<<>><<<<>>><<<>>>><<<>><<<><<><<<>>>><>><<<>><<>><<<<>>>><<<<>>><<<>>><<>>><<<>>>><<>><<<<>>><<<>>><<>><><<>>><<<>>>><<<<>>>><<<<>>>><<<>>><<<<>>>><>><<>>>><>>>><<>>>><<>>>><<<><<<>><<<>>><<>>>><<<>><<>>><<>>><<<<>>><><><<><>>>><<<>>><><<<<>><<<>><<<<>>>><<<<>>><<>><<<>>><>>><<<<>>>><>><<<><<<<>>>><<>>><>>><<>><<>>>><<<<><<><>>>><<<<>><>>>><>>>><><<<>>>><<>><<>>><<>><<>><>>>><<>><<>><<<<>>><<<<>>>><<<<>>><<><<<>>>><<><<><<<<>><<<><<><<<<>>>><>>><>>><<<<>>>><<<>>>><>><<>>>><<>>>><<<>>>><<<><<<><<<<>>>><<>><<><<<>>>><><<><<<>>><>>>><>><>>>><<<<>><<<<>><<<<>>>><><<<<><<><<<>><><<<>>>><><><>>><<<<>>><<<>>>><>><<<>>><<<>><>><<>><<<<>>>><<<>>><<<>>><<<<>>>><>>><<>><<<<>>>><<<>>><>>>><<<><<><<>><<<>><>>>><<<<><<<><<<<>><<<><<<<>>><<<<>>>><<<>>><>><<<>>>><<<<>>><>><<<>>><<<<>>><<<<><<<<>>><<>>>><<>>>><>>><<<>><<<<>>><<<<><<>><>>><<>>>><<<<><>>>><<<>>><><<<>>>><><>>>><>><<<>>><<<>>>><<<<>>><<<<>>><<<<>><<<><<<<><<<>>><>>><<<>><<<<>><<<>><<<><<<<><<><<<<>>><<>><<>>><><<><>>>><<>>><<<<><<>>><>>>><<<>>><<>>>><><<<>>><<><<<<>><><<><<<>>><<<>>>><<>>>><<>>>><<>>><<<<>>><<<>>><<<><<<>>><>><<<>><<>>>><<>>>><<<>>><<<<>><<<<>>>><<<<>>><<>>><<<<><>>>><>>><<>><<<<>>>><<<<><<>>>><<<>>><<<>><<>>><>><<<>>><<<<><<<<>>><>><<<>><<<<>>><<<><<<><<<>>><>><<>>>><>>><<<<>>><<<>>><>>>><<><><<<<><>>>><><>>>><<<<>><<<<>><>>><<>><<<><<>>>><<<>><<<>><<<>>><<<<>><<<>>>><>><<>>><<<<>>>><<>>>><<<>>><<<<>>>><<<>><><<<<>>>><>>>><<>>><<<<>>><<<>>>><<><<<>>><<>>>><<<<>><<<><<>>><<<>>><<<<><<>>>><><<<<>><<<>>>><<<<>><<<<>>><<<>><<<><>><<<>><<<<>>><<>><<>>><<><<<<>><>>>><<<>>>><<>>>><<>>><<>><<<<>><<<>>>><<>>><<<>>><<<<>><>>>><>>>><<<<>>>><<>><<>>><<<<>>>><>><>>><>><<>><<<<>>><<<>>>><<<<>><<<<><<>><<<<>><<<<>><<<<>>><<<>>>><<<>>><<<>><<<<>>>><<<<>>>><<<<><>><>>>><><<<><>><<<<><<<<>><<<>>>><<>>><<<<>>>><<<<><>>><<>>>><<<>>><<<>>>><<><<<<>><<<>>><>>><<><<<<>>><<<<>><<<<><<>>>><>>><<<>>><<<<><<>><<<><>>>><<>>><<<>>><<<><<>>>><<<>><<<><>><<>><<>><<<>>><<<>>>><<>>>><<<<><<>>>><<<<>><>><<>><<<<>><<>>><<>><<<><<<>>><<>>><<<<>>><<<<>><<<<>><<<<>>>><<<>><<<<>><><<>>>><<<<>>><<<>>><<>><<>>>><<<>>><<>><<<<>><<<>>>><<<<>>>><<><<<<>>><><<>>><<<<>>>><<<>>>><<>><><><<<<><<<><<<>>>><>>>><<<<>><<>>>><<<>>>><<>>><><<>>>><<<<>>><<<>>><<>>>><<<>>><<<>><<<>>>><>>><<<<>>>><<<<>>><<>><<><<<>><<>>>><<<>>><><>>><<>>>><<<>><<>>><<<<>>>><<<<>>>><<>>>><<>><><<>>>><<><<>><<<><<<>>>><<<<><<><<<<>><<<>><<<<><<>>>><<><<<>>>><<<>><>><<><<>>><>><<<<>>><<<>>><<>>><<<>><<>>>><<<>>>><<<<>><><<<>>><><<<<>>>><<<<><>>><>>><<<<>><<<>>>><<>>><<<<>>><<<>><<<><<>><<<>>>><<>>><<<>><>>><<<<>>><<>>>><<<<>>><<>>>><<<><<<<><><<<>><<><<>>><><<<>><<<><><<<><<>>>><<<<><>>><<<>>>><<<<>>><<>>><<><<<<>><<<>>><<<<>><><<>>>><>>>><>>>><<<<>>>><<<>><<<>><<<<>>><<<<><>>><<<<><>>>><><<<>><<<<><<>>>><<<<>>>><<>><<>>><><>>>><>><>>><<>>>><<<>>><<<<><<<>><<<<>>><<>>>><<<<>>><<<<>>>><<>>>><>><<><>>>><<<<>>><<<<><>>>><<<<>><<>>><<<<>><<<>><>>><<><<><<<<>><<><<<<>>>><>>>><<>>><>>><<<<>>>><<<>>><<<<><<<<>>><<>>><<>>>><<<><<<>>>><<<>>><<<><<<<><<>>>><<<>>>><<<<>>><>><<<<>><<><<>>><<><<<>>>><<<<>><<<>>><<><<<>>>><<><<<<><<<<>><<><>><<>>>><>><<<><>>>><<<<>><<<<>><<>>><<<>><<>>>><<<><<>><<<>>>><<<<>>>><><<><>>>><<<>>>><><<<<><<<<>>>><<>>>><<<<>><<<<>><<<<>>><<<><>><<>>>><<><<>>>><<<>><<>><<<<>>>><<<><<<>>>><<<>><<>>><<>>><><<>>>><><>><<<>>>><<<><>><<<<>>>><<><>>>><>>><<<<><<<<>><<>>>><<<><<>>><<>><<<>>>><<><<>>>><<<<>>><>>>><<><<<>>><><<<>>><<<>>><<<>><<<><<>>><<<>>>><<<<>><>>>><<>>>><<<>>>><>><<>>>><<>><<<>>><<>>><<><<>>>><<>><<>>><>>><<<<>>>><>>><<<<>><<<<><>>><>>>><<<>><<<<>>>><<<><>><><<<>>><<<>>>><>>><<>>><<<<>><<<>>>><<<>>>><<<<>><<<<>>><>>>><><><><<<<>>>><<<<><<<<><<<<>>>><<<>>>><<>><<<<><>><<>>><<<><><>><<<>>><<<<>>>><><<<<>>>><><<>>><<>><<>>><>><>><<<>><<>>>><>><<><<<>>>><<<>>><<<<>><<<<>>><<<<>>><<<<>>>><><<>><>>><<<<>>>><><<>>>><<<<>>><<>>><>>>><<>>><><<><<>>>><<<<><<<<><<>>>><<>>>><>>>><<<><>>><<<>>>><<<><<>>><<>><<<><<<<>>><<<>><<>>><>>>><>><<>>><><<<><>>>><<>>><<<><>><<<>><<<<>>>><<<<>><>>><<>>><>><<><<><<<<>><<<><<<>>><>>>><<<<>>><<>>>><<<>>><<>><<>><<<>>>><>><>><>>><<<<>>><<<>>>><<<<>>><<<>>>><>><<>>><<>><<>>>><<<>><<>>>><<<<><<<<><<<<>>><<<>><<<>><<<<>>><<<><<<<>><<>><<>>><<<>>><<>>><<<<><<<<>>><<>>>><<<>>><<>><<<<>><<<<>>>><<<<><<<>>>><<<<>>>><><>>><<>><<<>><<>>>><<>>>><<<>><<>>><<<<>>>><<<<>>>><<>>><<<<>>><<<<><<>>>><<<>>>><<<>><<<>><<>><<<<><<<<>>>><<<>>><<><>>><<><<>>>><<<>>>><<<<><<<<>>><<<<><<<>>>><<>>>><<>>><<>>><<<>><<><<<<><<>><<<<><<<><<<><<>><<<>>>><<<><<>><<>>>><<<><<<<>>><<<>>>><><><<><<<>>><<<<>>><<<>><<<>>>><>>>><<><<>><<>><>>>><<<>><<<>>><>>>><<<<>>><<<<>>><<<>>><<<>>>><<<<>>><<>>>><<<>>>><<<>>><>>>><<><<<>><<>>>><<<<>>><>>><<>>><<<<><>><<>>>><>>><<>>><<><>>><>>><><<><><<>><<<<>>>><>><<<<>>><<>>><<>><>>>><<<>>>><<<<>><<><<<<>>>><<<<><<><><<<<>><<>>>><<<><>>>><>><><<>><<<>>><<>>>><<><<<>>><<<<>>>><<<<>><<><<<>>><<<<>>>><<<>>>><<<>>>><<><<<>>>><<<<>>>><>><>>>><<>>>><<<>><<>>><<<>>>><<>>>><<<<>><>>>><<>>>><<<>>>><<<><<<>><<<<>>><><<<<><<<<>><<<>><<<>>>><<<><<><<<>><<<<>>>><<<>>><<>>><<>>><><<>>>><>>>><<><>>><><<<<>>>><>>><>>><<>><>>>><<<<>><<>><<><<<<>><<<<>>>><<<<>>>><<<><<<<>>>><<<<>>><<><<<><<><<>>><<<<>><<<>>>><<<>>>><<<>>>><<<>><>><<<><><<>>>><<<<><<<><<<<><<>>><<<>>>><<<<><<<<>><<>>>><<>><<><<<<>>>><><<<<>><<><<<>>><>>><>><>>>><<<<>><<>>>><><<<>>><<>>>><<<>>>><<<<>>><<>><<<<>><<>>>><<<>>>><<<<><>>><<<<>><<>><<<>>><><<>>><>><<<>>>><>><<<>>>><<<<><<>>>><><<<<>>><<<<>><<<>>>><<>>>><<<<>>>><<<<>><<><<<><<<>>>><<<<><<<<>>>><><>>><<>><<<>><<>><<>>>><>>><<<<><<<<>>><<>>>><<<>>>><>>>><<<<>><>>>><>>><<<>>>><>>>><<>>>><<><<>>>><>>>><><<<<><>>><<><<<>>>><>><<>>>><<<>><<>><<>>><<<><<<<>>><<<><<><>>><<><<><<<><<<>>>><<>>><<<<>><<<><>><<>>><<<<>>><>>><<>><<<>>>><>>>><<<<>><>><>><>>><<<<>><<>><><<>>><<<>>><<<><>><<<<>><<<>><<>><<<>>>><<>><<<<>>><>>><<><<>>><>>>><<><<<<>>>><<<<>>>><<>>>><<<>><<<>><<<>>>><>>><>>>><<><<<>>><<><<>>><>><<<<><<<<>>>><<<>>><>><<>>><>><>><<><<<<>><><>>><<<><<<<>>>><<<<><<<<>>><<<<>>>><<<>>>><<>>>><<<<><>>>><>>><<<<><<>><<<<><<<<>><>><<<>>><><<<<>><>>><>>><<><><<<<>><<<<>>><<<>>><>>>><<>>><<>>><<>><>>><<<>><><<>>>><><<<>><<<>>>><<<>>><>><<>>><<<<>><<<<>>>><<<<>><<<>>><<<<>>><><>>>><<>><<>>>><<<<><<<<>>>><<>><<>><>><<<<><<>>>><<<>>><>><<<<>>>><<>><<<>>>><<>>><<<><<<>><<<<><>><><<<<>>>><<<>><<<>>><<<>>>><<<>>><<<<>>>><<<>>><>><<<><<<>><><<>>>><>><>>><<<>>>><<>>>><>>>><<>>><<<<><<<<>><<<<>><<<<>>><<<<>>><<>>><><<<>>>><><<<>>><<>>>><<<<>>>><<>><>>>><<<<>><<>>><>><>>>><<>>><<<<>>>><<>>><<>>><><<<<>><<<>>><<<<>>>><>>><<><<>><>>><<>>><<><><<<><>>><<><<<>>>><>>><<<>><<>><<><<<<>><><<<<>>>><<<>><>>><>>><<<><>>>><<<><>><<<<><<<<><<<>><<>><<>><<<<>>>><<>>><<>><<<<>>>><>>><>>><<<<>>>><>><<<<>>><<>>>><<>>>><<<>><<<><<>>><>><<<<>>>><<>><<<>>><>><<<>><>>>><>>><<<>>><>><>><<<>><<<>><<<<>><<<>><<<<><>>><<<>>><<>>><<<>><<<>><>><<<<>><<>>><<<>>><<<>><<<<><<<>>>><>><<>>>><<<><>>>><<<>>>><>>><>><<>><<>>><<<<>><<<<>>>><>>><>>>><<<<><<<>><<>>>><<<<>>><><<>>>><>><<>>>><<<><<><>>>><<<>><>>><<<>>>><<<<><<>><>>><>>>><><<<>>>><<<<><><<<>>><<<>><><<<>>><<<>><<<>><<<><>>><><<<<><<<<>>>><<<><<>><<<<><<>><<<<>>>><<<<><>>>><<<><>>>><<<<>>><<<<><<><<<><<>><<>><<<<>>>><<<>>>><<<<>>>><<<>>>><<<<>>>><<<<>>>><>>><<<<>>><<<<>><>>>><>><>><>>><<>>>><>>>><<<<>><<<<>><>><<<<><<>>><<>><>>>><<<<>>><<>><<<<>>>><<<>>><>><<<<>>><<<>>><<<<>>><<<><<<<><<<<>>>><<<>><<<>>><>>><<<<>>>><<>>>><<<><<<>>><<>>>><<<>><>>><>>>><<<<>><<<>><<>>>><>>>><<<>><<><><<<<>>>><<>>><<>>>><<>>><<>>><<>>>><>><><<<<>>><<<<>>><<<>><<<<>><>>><<>>><<><<<>>>><<>><><>><<><>>><<<>><<><<<><<<>><<<<><<<<>>>><<<><<<<>>>><<<<><>><>><<<>><<<<>>>><<>>>><<>>><>>><<<>>>><<<><<><<<>><<><<>>>><<<><<<>><<<<>>>><<>>><<<<>><<>><><>><<>>><<<<>><<><<><<>>><<>>><<<<>>><<<<>><<<<><><>>><<<<><<>>>><<<<><>>><<>><<<<>><<>><<<><<<><<<<>>>><<>>><<>><>><>>>><>>>><>><<>>>><<<<>>><>><<><<<>>>><<<>>><<<>>><<><<>><<>>><<>>><<<<><<<<>>>><><<<<><<>>>><<<<><><<>>>><>><<>>>><>>>><<<>><<<<>>>><<<<>>>><<<<>>><>><<><<>>><>>>><<<>><<<>>><<>>><>>><<<>>>><>><<<>>>><<<<><<><<>>><<<<>>><<<>>>><<>>>><>><<<><<<>><<<<><><<><<<>>><<<>>><>>><>>><<<<>><<<><>>>><<<<><><<<<>>><<<>>>><<>><<<<>>>><<<>>><<<<>>>><<>>><<>>>><<<<>><><>>><>>>><<<<>>>><<<>>><<><<>><>>>><<>>>><>>>><<>>>><<<>><<<>>><<<><<>>><<<>>>><<>>>><<>>><<<<>><>><<<<><<>><<>><<>>><<<><<>>>><>>><<<<><<<<>>>><><<>>>><<<>><<>>>><<<>>><<<<>>>><<<<>>>><><>>>><<<>><<<>>><<>><<<<>>>><>>><<><<<><<<<>>>><<>>><><<<>>>><<><><>><><<>>>><<<<>>><>>><<<<>>><<>>>><<>><<<>>>><<<>>><<<<>>><<<>><<<<><>><<<><><<><<<<><<<><<<>>><<><<<>>>><<<>>><<<<>>><<<<>>>><<<>><<<<>>><<><><<<><>>><<<>>>><>>><<<<>>><<<<>>>><<<>>>><<<>><<>><<>>>><<>>><<>><><<<>>>><<<<><>>><<<<>>><<<>><<<<>><<>>>><<>>><<<>>>><<>><<<>>>><<<<>>><><>><<<>>><<<<>>><<<<>><<<>>><>>><<>><><<<>><><>>><<>><<>><<<>>>><<>>>><><>><<<>>><>><<>><<<<>>>><<>>>><<<>><<>>><>>><<<<>><<<<>>><<>>><<>>><<<<>><<>><<><<<<>>>><<>>>><>>><<>>><<>>><<<>><<<>><<<<>><<<<><>>><<<<>><<<><><<<<>>><<>>>><>>>><<>><<<<><<<<>>><<<>>><<<<>>><<<>>><<<>><<<>><>>><<>>>><<<><<<>>>><<<<>>>><>>><<><<<><><<<>><>>><<<>>>><>><<<>>>><<<><<>>
""".trimIndent()

/**
--- Day 17: Pyroclastic Flow ---
Your handheld device has located an alternative exit from the cave for you and the elephants. The ground is rumbling almost continuously now, but the strange valves bought you some time. It's definitely getting warmer in here, though.

The tunnels eventually open into a very tall, narrow chamber. Large, oddly-shaped rocks are falling into the chamber from above, presumably due to all the rumbling. If you can't work out where the rocks will fall next, you might be crushed!

The five types of rocks have the following peculiar shapes, where # is rock and . is empty space:

####

.#.
###
.#.

..#
..#
###

#
#
#
#

##
##
The rocks fall in the order shown above: first the - shape, then the + shape, and so on. Once the end of the list is reached, the same order repeats: the - shape falls first, sixth, 11th, 16th, etc.

The rocks don't spin, but they do get pushed around by jets of hot gas coming out of the walls themselves. A quick scan reveals the effect the jets of hot gas will have on the rocks as they fall (your puzzle input).

For example, suppose this was the jet pattern in your cave:

>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
In jet patterns, < means a push to the left, while > means a push to the right. The pattern above means that the jets will push a falling rock right, then right, then right, then left, then left, then right, and so on. If the end of the list is reached, it repeats.

The tall, vertical chamber is exactly seven units wide. Each rock appears so that its left edge is two units away from the left wall and its bottom edge is three units above the highest rock in the room (or the floor, if there isn't one).

After a rock appears, it alternates between being pushed by a jet of hot gas one unit (in the direction indicated by the next symbol in the jet pattern) and then falling one unit down. If any movement would cause any part of the rock to move into the walls, floor, or a stopped rock, the movement instead does not occur. If a downward movement would have caused a falling rock to move into the floor or an already-fallen rock, the falling rock stops where it is (having landed on something) and a new rock immediately begins falling.

Drawing falling rocks with @ and stopped rocks with #, the jet pattern in the example above manifests as follows:

The first rock begins falling:
|..@@@@.|
|.......|
|.......|
|.......|
+-------+

Jet of gas pushes rock right:
|...@@@@|
|.......|
|.......|
|.......|
+-------+

Rock falls 1 unit:
|...@@@@|
|.......|
|.......|
+-------+

Jet of gas pushes rock right, but nothing happens:
|...@@@@|
|.......|
|.......|
+-------+

Rock falls 1 unit:
|...@@@@|
|.......|
+-------+

Jet of gas pushes rock right, but nothing happens:
|...@@@@|
|.......|
+-------+

Rock falls 1 unit:
|...@@@@|
+-------+

Jet of gas pushes rock left:
|..@@@@.|
+-------+

Rock falls 1 unit, causing it to come to rest:
|..####.|
+-------+

A new rock begins falling:
|...@...|
|..@@@..|
|...@...|
|.......|
|.......|
|.......|
|..####.|
+-------+

Jet of gas pushes rock left:
|..@....|
|.@@@...|
|..@....|
|.......|
|.......|
|.......|
|..####.|
+-------+

Rock falls 1 unit:
|..@....|
|.@@@...|
|..@....|
|.......|
|.......|
|..####.|
+-------+

Jet of gas pushes rock right:
|...@...|
|..@@@..|
|...@...|
|.......|
|.......|
|..####.|
+-------+

Rock falls 1 unit:
|...@...|
|..@@@..|
|...@...|
|.......|
|..####.|
+-------+

Jet of gas pushes rock left:
|..@....|
|.@@@...|
|..@....|
|.......|
|..####.|
+-------+

Rock falls 1 unit:
|..@....|
|.@@@...|
|..@....|
|..####.|
+-------+

Jet of gas pushes rock right:
|...@...|
|..@@@..|
|...@...|
|..####.|
+-------+

Rock falls 1 unit, causing it to come to rest:
|...#...|
|..###..|
|...#...|
|..####.|
+-------+

A new rock begins falling:
|....@..|
|....@..|
|..@@@..|
|.......|
|.......|
|.......|
|...#...|
|..###..|
|...#...|
|..####.|
+-------+
The moment each of the next few rocks begins falling, you would see this:

|..@....|
|..@....|
|..@....|
|..@....|
|.......|
|.......|
|.......|
|..#....|
|..#....|
|####...|
|..###..|
|...#...|
|..####.|
+-------+

|..@@...|
|..@@...|
|.......|
|.......|
|.......|
|....#..|
|..#.#..|
|..#.#..|
|#####..|
|..###..|
|...#...|
|..####.|
+-------+

|..@@@@.|
|.......|
|.......|
|.......|
|....##.|
|....##.|
|....#..|
|..#.#..|
|..#.#..|
|#####..|
|..###..|
|...#...|
|..####.|
+-------+

|...@...|
|..@@@..|
|...@...|
|.......|
|.......|
|.......|
|.####..|
|....##.|
|....##.|
|....#..|
|..#.#..|
|..#.#..|
|#####..|
|..###..|
|...#...|
|..####.|
+-------+

|....@..|
|....@..|
|..@@@..|
|.......|
|.......|
|.......|
|..#....|
|.###...|
|..#....|
|.####..|
|....##.|
|....##.|
|....#..|
|..#.#..|
|..#.#..|
|#####..|
|..###..|
|...#...|
|..####.|
+-------+

|..@....|
|..@....|
|..@....|
|..@....|
|.......|
|.......|
|.......|
|.....#.|
|.....#.|
|..####.|
|.###...|
|..#....|
|.####..|
|....##.|
|....##.|
|....#..|
|..#.#..|
|..#.#..|
|#####..|
|..###..|
|...#...|
|..####.|
+-------+

|..@@...|
|..@@...|
|.......|
|.......|
|.......|
|....#..|
|....#..|
|....##.|
|....##.|
|..####.|
|.###...|
|..#....|
|.####..|
|....##.|
|....##.|
|....#..|
|..#.#..|
|..#.#..|
|#####..|
|..###..|
|...#...|
|..####.|
+-------+

|..@@@@.|
|.......|
|.......|
|.......|
|....#..|
|....#..|
|....##.|
|##..##.|
|######.|
|.###...|
|..#....|
|.####..|
|....##.|
|....##.|
|....#..|
|..#.#..|
|..#.#..|
|#####..|
|..###..|
|...#...|
|..####.|
+-------+
To prove to the elephants your simulation is accurate, they want to know how tall the tower will get after 2022 rocks have stopped (but before the 2023rd rock begins falling). In this example, the tower of rocks will be 3068 units tall.

How many units tall will the tower of rocks be after 2022 rocks have stopped falling?

Your puzzle answer was 3161.

--- Part Two ---
The elephants are not impressed by your simulation. They demand to know how tall the tower will be after 1000000000000 rocks have stopped! Only then will they feel confident enough to proceed through the cave.

In the example above, the tower would be 1514285714288 units tall!

How tall will the tower be after 1000000000000 rocks have stopped?

Your puzzle answer was 1575931232076.

Both parts of this puzzle are complete! They provide two gold stars: **
 */