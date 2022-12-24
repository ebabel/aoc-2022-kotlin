package y2022

import expecting
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {

    measureTime {
        val testInput = Day24(testInput)
//        testInput.part1().expecting(18L)
//        testInput.part2().expecting(0L)
//
        val realInput = Day24(input)
        realInput.part1().expecting(785L)
//        realInput.part2().expecting(0L)
    }.also {
        println("Took ${it.inWholeSeconds} seconds or ${it.inWholeMilliseconds}ms.")
    }
}


class Day24(private val input: String) {

    private val UP = 0
    private val DOWN = 1
    private val LEFT = 2
    private val RIGHT = 3
    private val WALL = 4
    private val OPEN = 5

    private fun Int.isBlizzard(): Boolean = when (this) {
        UP, DOWN, LEFT, RIGHT -> true
        else -> false
    }

    private val allOrthoDirections = listOf(
        Point(0, -1),
        Point(0, 1),
        Point(-1, 0),
        Point(1, 0),
    )

    private fun Point.applyDirection(direction: Int) {
        this.x += allOrthoDirections[direction].x
        this.y += allOrthoDirections[direction].y
    }

    fun part1(): Long {
        val map = mutableListOf<Pair<Point, Int>>()
        input.lines().forEachIndexed { y, s ->
            s.forEachIndexed { x, c ->
                when (c) {
                    '#' -> WALL
                    '.' -> OPEN
                    '>' -> RIGHT
                    '<' -> LEFT
                    '^' -> UP
                    'v' -> DOWN
                    else -> error("unknown char $c")
                }.also {
                    if (it != OPEN) {
                        map.add(Point(x, y) to it)
                    }
                }
            }
        }

        fun Int.debug() = when (this) {
            WALL -> '#'
            OPEN -> '.'
            RIGHT -> '>'
            LEFT -> '<'
            UP -> '^'
            DOWN -> 'v'
            else -> error("fdffdd")
        }

        val walls = map.filter { it.second == WALL }.map { it.first }

        val maxY = map.maxOf { it.first.y }
        val maxX = map.maxOf { it.first.x }
        val minY = map.minOf { it.first.y }
        val minX = map.minOf { it.first.x }
        val maxWindY = map.maxOf { it.first.y } - 1
        val maxWindX = map.maxOf { it.first.x } - 1
        val minWindY = map.minOf { it.first.y } + 1
        val minWindX = map.minOf { it.first.x } + 1

        val blizzards = map.filter { it.second.isBlizzard() }

        val blizzardPoints = blizzards.map { it.first }
        val start = Point(input.lines().first().indexOf("."))
        val end = Point(input.lines().last().indexOf("."),input.lines().size-1)
        var firstGo = true
        var allDone = false
        var isRevving = false
        var isRevvingClear = false
        var gotSnax = false
        var gotSnaxClear = false

        val paths2 = mutableSetOf<Point>()
        paths2.add(start)
        var minutes = 0
        while (!allDone) {
            minutes++
//            println("Minutes $minutes")
            map.forEach {
                if (it.second.isBlizzard()) {
                    it.first.applyDirection(it.second)
                    if (it.first in walls) {
                        when (it.second) {
                            UP -> it.first.y = maxWindY
                            DOWN -> it.first.y = minWindY
                            LEFT -> it.first.x = maxWindX
                            RIGHT -> it.first.x = minWindX
                            else -> error("wrong way probs")
                        }
                    }
                }
            }

            val paths3 = mutableSetOf<Point>()
            paths2.forEach { pathPoint ->

                if (pathPoint == end && firstGo) {
                    firstGo = false
                    isRevving = true
                    isRevvingClear = true
                }
                if (pathPoint == start && isRevving) {
                    isRevving = false
                    gotSnax = true
                    gotSnaxClear = true
                }

                if (pathPoint == end && gotSnax) {
                    allDone = true
                }

                paths3.addAll(allOrthoDirections.map {
                    it + pathPoint
                }.plus(pathPoint)
                    .filter { it.x in 0..maxX && it.y in 0..maxY && it !in walls }
                    .filter {
                        it !in blizzardPoints
                    })

            }
            if (gotSnaxClear) {
                paths3.clear()
                paths3.add(start)
                gotSnaxClear = false
//                println("turning around")
            } else if (isRevvingClear) {
                paths3.clear()
                paths3.add(end)
                isRevvingClear = false
//                println("turning around")
            }
            paths2.clear()
            paths2.addAll(paths3)

//            println("done with paths2 phase ${paths2.size}")

//            (minY..maxY).forEach { y ->
//                (minX..maxX).forEach { x ->
//                    val z = map.filter { it.first.x == x && it.first.y == y }
//                    if (z.size == 0 && Point(x,y) in paths2) {
//                        print('E')
//                    } else if (z.size == 1) {
//                        print(z.first().second.debug())
//                    } else if (z.size == 0) {
//                        print('.')
//                    } else {
//                        print(z.size)
//                    }
//                }
//                println()
//            }
        }

        return minutes.toLong() -1
    }

    fun part2(): Long {

        return -1
    }
}


private class Point(var x: Int = 0, var y: Int = 0) {


    override fun toString(): String = "[$x, $y]"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

private operator fun Point.plus(other: Point): Point = Point(x + other.x, y + other.y)

private val testInput =
    """
#.######
#>>.<^<#
#.<..<<#
#>v.><>#
#<^v^^>#
######.#
""".trimIndent()
private val input =
    """
#.####################################################################################################
#>^v<><>.<<<^>v>^v<v^<>v<>^<v>><v<<^.v.v.v<v^>^<>v^^>^^^^v>v<^>.v.<<^.v^>><.>><^v^^v^v>><.v>.<v<<>.><#
#<.<<<<v<<<>v^v^><v>>>>v>^>v.^v><^><>^<><><v<<.v<>^<^^v<vv^<v><^^vvv<<<..<v<><^v><<>><.<<v.v<vv>^^^^>#
#<>>.v^^<^<.v><^^^v>.<.v^<>v>>^v^<^<^>>v><<^<<v.v>^><v>v.><<><<^v<<^>.vv<>^^v<>v^v<<.^^v^v>><<>^<<>><#
#>..v<><^v<>.>>>.^<.^^v<v>v>v<v^^>><<v>^>vv^>>><^^<v<v<<v^^<>^^>^<v><<vvvv>>>vv>^><><^^^^<<.v.<^<^>>.#
#<<^>v^>^<<>v<^.v<^>.><v^v<v>>^><><<^<^>vv><>>.<><.v<^<>.<.<>>v^v.>^^>^..<<^<>^<<<>>..>^.<^^v>>>>v^^>#
#>v^^^v>^vvv<<v^^<v>^<vv<^^v<<>>v<>vvv<v>>v.>^vvv><v>v>v<<<vv><^^.^^>^.<v.^<.>v>v><vv>><^^<^>^^v<><<>#
#<>.v^>><<<vv<^>^><<.<.<.<>v>.>^^^<>.<v<>.<<<><v<><>><^.vv>><<^^>.<^.>>.<>^^..<<^.v.<>v^>>.v^<>^v<^<<#
#<>>><>><<><>^.<<><<v^.vv><v<<<v<v^^v<v>^.^<>><^>^^.^>><^^<<^>^v<^.>^.<>^>>v^vv<>..<><>^><>v.<>v><<^.#
#>>>>>v^<^^.<^v><^.v>v^<>>v>>>>.v^<><<<>>^v^v<.v<.<<v>^><.v^vv.<v.v.<<>>v<v^<>.v^>v.>v^.^^^vv>^v><>><#
#<..^>v<<^v<<<<.>vvvv<..v>^vv>^<<<.v<>vvvvv><<^>^<><^^^>>.^><vv><^>v>vv><.>^^v>>^><<>^.<<<^><^v<v>>v>#
#><vv>>v..<<..vv<v^.v.><>vv^>v<vvvv^v<>^<<><>^v><>^v^v<><^.^>^>.>><vvv<v>>v<v<v<><<<vv^<<<^>vv>.^^<v<#
#>^<v<.^<>.<<<<^<<^>>^>^v<<..>v<v^><^<v.<.v<>^<v>v.>vv^v<^vvv^vv>^v.^<.<^<>v>^<^<^^<^>v>v<^v^>>v<<<.>#
#.<^v>^.^v^>>>vv<>v<v.vv<<v><^v<>>v^<^v<>v^.><^>.>>^><^>><>..><.><<<>>^><.v>.<.^>^><>>>v^^v<^<^>><<<<#
#><<>^><<v.<>.^vv>.^^^<<<>vvv.<.<>^^<<v.^v.><>^<>v<^v^<<>v>>^.v.><v^v<v>^<<^v^>>>v<vv<^<v^<>vv.v.>vv<#
#.v.<^^<^<^<^<^<.v^>^<>><.<>^^<<^^.^.<.^>>v><<>^<^>>>><^.>.vv<>v.vv<v^.v^<^<>^v><>>>^>>.^vv><v.>^><>>#
#><v>^.^vv>v<<v^v.<vv^<^^>.vv>.<^>.^<.^>v>>v^>v^>.>.><>>v^^^><>.<>>.^v<<^<<><v<^^^<<>><^><v^<>><>^^^>#
#<v<^>vv><<>^<<>>^><^^vv>>.>.>^><vv^^><>>v>^^.v<<<<><v^><>>v^^^<<^v^>>>^<>v^^<v^<>^v><v<<<>>>v><><<v>#
#>>>^<<^v<>>>v^v^v^^^^<<<<v<<>vv>.^v<<.v^^<v<>>vvv.>>^<>v>^v^v><.^v>^<>v<<.<^^^<vv^^^^vv<v>^>^<<..^v>#
#<v<.vvv.>v^>>v>>.^<<v.^^><>^<v^>vv<<^><><<>^vv^v<>v<><v>.v^>..<>^>>v>^<vv><>>^^v<.<>>.>v<vv>>^>v<^>>#
#.vv^>^v<vv>v>.v^<>^>^.^<>^><<^<.^>v^v><<><<<^<^<>v>v>v<.^>.>^^<v.<<<v^>v^v^<<>>>v>^>^.>^<>^<^^^>vv^<#
#<<.<^v^v>v<v^<<.v..v^>>>>v>.>><<<<.vvv>vv>>^v<><<^<<<vv.^>^^vv.><v^^>^>vv>v.^vvv<<.^<..<<.<<^<..<.>>#
#<<>^^v><<v>^vv.<v^>^.^<v>.>vv^v><vv^.^.vv>v<vv><v>^v^^>^^>v<<^<v><<.vv<<>.^v<>v^>^^v^^^<v<^.v^>vvv^>#
#<.v>>v^><<^>^v^>^<>v.<v^.>.^v^<.^>v<>^<><>^>>^>^^<<^.>>v<.^v^.^vv><.<<v^v<v><>^^<>>^v<<><>.^v<<><>>>#
#<>vv<v>>v>><.>>v.<<v.v^vv^.<<<><vv>.>^^vvv^vv><>^>^v^<>><>>v<>>>>>v^><<^<.^<<<^>>^^.<>>.<<>v^v><><><#
#<^^^v<..^.<^v<^v<v>vv>>vv..^vvvv<v.^v>><>^><<^><^v.<><>>^>>v>v^>.v><>^v>^^<<<^v^.<v.>><>>>.>>>v>v>^>#
#>>vv^.<<vv.v<v>>v^^<<vv^.<>.v>>>><><.v^^v^^v.><>^^v<^v^v<.>^vv>.vvv>><v.v>><v>^>v<^.>^><<>v><<>>.>v<#
#>vv>^<<^>^<<>^>v<>^<<^><^^<^.<v<^>vvvv^<<>>v<<.<>v.^>..vvv<>v<><<<^v>v>^><^v>>^>.^>v^v<v<^<^<><^<vv>#
#><<v^vv^>>vv<>^v>>^^.<<<v>.v.>^v<<v<^<v><<vvvv.>>v.v^vv<<><<>^>>.>^v^v>vvv<..v>>v<^<>v>v<^>^>v>><.>>#
#>v<<^v^<^v<^<<>vv^vv><v><<<^><>>v<v>^^<<>.^<>>>^<v>^..<^^v<<<><>v<^^v.>vv^v^^v<v>vv<^><><^<v<^^<^<v<#
#><^.^<v>>>vv<.<>>.>v^<^><>v<v<><><vv<v.^^^<>^<<<vv.vv<>v<v^><^<>>v^>^v^>>v>v^vvvv<v^^^>.^<><v^<v^.<>#
#<^v<^<.v^<.>>^><<<^v<...<v.^>>v<^v.><><>v.>><.>v>v^..<^^^>>>>>v^.<<>^<.>^<>><^>>>^<>>^^^>v.>^vvv.^>>#
#>^>v^^.<v<>>>.v^<^v<<.><<^<><^v<>^<<^.^<<vv^.><><vv<v^^>>^>.v>v<>v>^.^<>^<>>>><<.><<>.^>>vv^><vv>>>>#
#<<.>..^^vvvv>>.v<.^^>v<^<v><<^<^.<^>^.v^><v>^...^^.><.<^<v.v>vv.<<^<>.><.<^>^>><<>.^v<^^v^>.<^v^v>><#
#<^><v^v^>v^<v^^>vv^^<v<>v<>>><v>v^^^v<^v<^>.^^><>v.<<^vvv.v>^>^<vv<v><^>vv<>v<<<^vv^<^>>^.<^>^v.<<<>#
#<>v.^.>vv^.v<^<^vvv<v^^v<>>v<<v^>><>>v<><v^>^^vv><>^<v><>v^>^>>><vv<<>v^>v>^<<^^<>.^<<^>vvvvv>^^.>^.#
####################################################################################################.#
""".trimIndent()


/**
--- Day 24: Blizzard Basin ---
With everything replanted for next year (and with elephants and monkeys to tend the grove), you and the Elves leave for the extraction point.

Partway up the mountain that shields the grove is a flat, open area that serves as the extraction point. It's a bit of a climb, but nothing the expedition can't handle.

At least, that would normally be true; now that the mountain is covered in snow, things have become more difficult than the Elves are used to.

As the expedition reaches a valley that must be traversed to reach the extraction site, you find that strong, turbulent winds are pushing small blizzards of snow and sharp ice around the valley. It's a good thing everyone packed warm clothes! To make it across safely, you'll need to find a way to avoid them.

Fortunately, it's easy to see all of this from the entrance to the valley, so you make a map of the valley and the blizzards (your puzzle input). For example:

#.#####
#.....#
#>....#
#.....#
#...v.#
#.....#
#####.#
The walls of the valley are drawn as #; everything else is ground. Clear ground - where there is currently no blizzard - is drawn as .. Otherwise, blizzards are drawn with an arrow indicating their direction of motion: up (^), down (v), left (<), or right (>).

The above map includes two blizzards, one moving right (>) and one moving down (v). In one minute, each blizzard moves one position in the direction it is pointing:

#.#####
#.....#
#.>...#
#.....#
#.....#
#...v.#
#####.#
Due to conservation of blizzard energy, as a blizzard reaches the wall of the valley, a new blizzard forms on the opposite side of the valley moving in the same direction. After another minute, the bottom downward-moving blizzard has been replaced with a new downward-moving blizzard at the top of the valley instead:

#.#####
#...v.#
#..>..#
#.....#
#.....#
#.....#
#####.#
Because blizzards are made of tiny snowflakes, they pass right through each other. After another minute, both blizzards temporarily occupy the same position, marked 2:

#.#####
#.....#
#...2.#
#.....#
#.....#
#.....#
#####.#
After another minute, the situation resolves itself, giving each blizzard back its personal space:

#.#####
#.....#
#....>#
#...v.#
#.....#
#.....#
#####.#
Finally, after yet another minute, the rightward-facing blizzard on the right is replaced with a new one on the left facing the same direction:

#.#####
#.....#
#>....#
#.....#
#...v.#
#.....#
#####.#
This process repeats at least as long as you are observing it, but probably forever.

Here is a more complex example:

#.######
#>>.<^<#
#.<..<<#
#>v.><>#
#<^v^^>#
######.#
Your expedition begins in the only non-wall position in the top row and needs to reach the only non-wall position in the bottom row. On each minute, you can move up, down, left, or right, or you can wait in place. You and the blizzards act simultaneously, and you cannot share a position with a blizzard.

In the above example, the fastest way to reach your goal requires 18 steps. Drawing the position of the expedition as E, one way to achieve this is:

Initial state:
#E######
#>>.<^<#
#.<..<<#
#>v.><>#
#<^v^^>#
######.#

Minute 1, move down:
#.######
#E>3.<.#
#<..<<.#
#>2.22.#
#>v..^<#
######.#

Minute 2, move down:
#.######
#.2>2..#
#E^22^<#
#.>2.^>#
#.>..<.#
######.#

Minute 3, wait:
#.######
#<^<22.#
#E2<.2.#
#><2>..#
#..><..#
######.#

Minute 4, move up:
#.######
#E<..22#
#<<.<..#
#<2.>>.#
#.^22^.#
######.#

Minute 5, move right:
#.######
#2Ev.<>#
#<.<..<#
#.^>^22#
#.2..2.#
######.#

Minute 6, move right:
#.######
#>2E<.<#
#.2v^2<#
#>..>2>#
#<....>#
######.#

Minute 7, move down:
#.######
#.22^2.#
#<vE<2.#
#>>v<>.#
#>....<#
######.#

Minute 8, move left:
#.######
#.<>2^.#
#.E<<.<#
#.22..>#
#.2v^2.#
######.#

Minute 9, move up:
#.######
#<E2>>.#
#.<<.<.#
#>2>2^.#
#.v><^.#
######.#

Minute 10, move right:
#.######
#.2E.>2#
#<2v2^.#
#<>.>2.#
#..<>..#
######.#

Minute 11, wait:
#.######
#2^E^2>#
#<v<.^<#
#..2.>2#
#.<..>.#
######.#

Minute 12, move down:
#.######
#>>.<^<#
#.<E.<<#
#>v.><>#
#<^v^^>#
######.#

Minute 13, move down:
#.######
#.>3.<.#
#<..<<.#
#>2E22.#
#>v..^<#
######.#

Minute 14, move right:
#.######
#.2>2..#
#.^22^<#
#.>2E^>#
#.>..<.#
######.#

Minute 15, move right:
#.######
#<^<22.#
#.2<.2.#
#><2>E.#
#..><..#
######.#

Minute 16, move right:
#.######
#.<..22#
#<<.<..#
#<2.>>E#
#.^22^.#
######.#

Minute 17, move down:
#.######
#2.v.<>#
#<.<..<#
#.^>^22#
#.2..2E#
######.#

Minute 18, move down:
#.######
#>2.<.<#
#.2v^2<#
#>..>2>#
#<....>#
######E#
What is the fewest number of minutes required to avoid the blizzards and reach the goal?

Your puzzle answer was 262.

--- Part Two ---
As the expedition reaches the far side of the valley, one of the Elves looks especially dismayed:

He forgot his snacks at the entrance to the valley!

Since you're so good at dodging blizzards, the Elves humbly request that you go back for his snacks. From the same initial conditions, how quickly can you make it from the start to the goal, then back to the start, then back to the goal?

In the above example, the first trip to the goal takes 18 minutes, the trip back to the start takes 23 minutes, and the trip back to the goal again takes 13 minutes, for a total time of 54 minutes.

What is the fewest number of minutes required to reach the goal, go back to the start, then reach the goal again?

Your puzzle answer was 785.

Both parts of this puzzle are complete! They provide two gold stars: **
 */