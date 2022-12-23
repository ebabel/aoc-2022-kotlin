package y2022

import Point
import expecting
import plus
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    measureTime {
        val testInput = Day23(testInput)
//        testInput.part1().expecting(110L)
//        testInput.part1().expecting(0L)
//
        val realInput = Day23(input)
//        realInput.part1().expecting(3862L)
        realInput.part1().expecting(913L)
    }.also {
        println("Took ${it.inWholeSeconds} seconds or ${it.inWholeMilliseconds}ms.")
    }
}

private val allOrthoDirections = listOf(
    Point(0, -1),
    Point(0, 1),
    Point(-1, 0),
    Point(1, 0),
)

private val allDirections = listOf(
    Point(1, 0),
    Point(1, 1),
    Point(1, -1),
    Point(0, 1),
    Point(0, -1),
    Point(-1, 0),
    Point(-1, 1),
    Point(-1, -1),
)

class Day23(private val input: String) {


    private val UP = 0
    private val DOWN = 1
    private val LEFT = 2
    private val RIGHT = 3


    class Elf(
        var point: Point,
        var nextPoint: Point? = null,
    ) {
        fun anyElvesInAnyDirection(elves: List<Elf>) =
            elves.any { elf ->
                elf.point in allDirections.map {
                    point + it
                }
            }

    }

    fun look(elf: Elf, direction: Int): List<Point> {
        return when (direction) {
            RIGHT -> listOf(
                Point(elf.point.x + 1, elf.point.y - 1),
                Point(elf.point.x + 1, elf.point.y),
                Point(elf.point.x + 1, elf.point.y + 1)
            )

            DOWN -> listOf(
                Point(elf.point.x - 1, elf.point.y + 1),
                Point(elf.point.x, elf.point.y + 1),
                Point(elf.point.x + 1, elf.point.y + 1)
            )

            LEFT -> listOf(
                Point(elf.point.x - 1, elf.point.y - 1),
                Point(elf.point.x - 1, elf.point.y),
                Point(elf.point.x - 1, elf.point.y + 1)
            )

            UP -> listOf(
                Point(elf.point.x - 1, elf.point.y - 1),
                Point(elf.point.x, elf.point.y - 1),
                Point(elf.point.x + 1, elf.point.y - 1)
            )

            else -> error("asdffdfd")
        }
    }

    fun part1(): Long {
        val elves = mutableListOf<Elf>()
        var currentlyFirstLook = 0
        val map = input.lines().forEachIndexed { indexY, s ->
            s.forEachIndexed { indexX, c ->
                if (c == '#') {
                    elves.add(Elf(Point(indexX, indexY), ))

                }
            }
        }
        (elves.minOf { it.point.y }..elves.maxOf { it.point.y }).forEachIndexed { indexY, y ->
            (elves.minOf { it.point.x }..elves.maxOf { it.point.x }).forEach {x ->
//                    println("indexy $indexY")
                if (Point(x, y) in elves.map { it.point }) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println("  $y")
        }
        println()
        println()
        println()


        var movesHappened = true
        while (movesHappened) {
            movesHappened = false
            val pointsRequested = mutableListOf<Point>()

            elves.forEach { elf ->

                if (elf.anyElvesInAnyDirection(elves)) {
                    // look in each direction in order
                    (0..3).map {
                        (it + currentlyFirstLook)%4
                    }.firstOrNull { dir ->
//                        println("looking at $dir $currentlyFirstLook ${dir}")
                        elves.map { it.point }.none { it in look(elf, dir) }
                    }?.let {
                        val nextPoint = elf.point + allOrthoDirections[it]
//                        println("elf moving $it ${allOrthoDirections[it]} to $nextPoint")
                        pointsRequested.add(nextPoint)
                        elf.nextPoint = nextPoint
                    } ?: run {
//                        println("elf not moving")
                    }
                } else {
//                    println("elf not moving")
                    // do not move
                }

            }

            elves.filter { it.nextPoint != null }.forEach { elf ->
                if (pointsRequested.count { it == elf.nextPoint } > 1) {
                    // do not move
                    elf.nextPoint = null
                } else {
                    movesHappened = true
                    elf.point = elf.nextPoint!!
                }
            }

            elves.forEach { it.nextPoint = null }


//            println("miny ${elves.minOf { it.point.y }}")
//            var y = elves.minOf { it.point.y }
//
//            (elves.minOf { it.point.y }..elves.maxOf { it.point.y }).forEachIndexed { indexY, y ->
//                (elves.minOf { it.point.x }..elves.maxOf { it.point.x }).forEach {x ->
////                    println("indexy $indexY")
//                    if (Point(x, y) in elves.map { it.point }) {
//                        print("#")
//                    } else {
//                        print(".")
//                    }
//                }
//                println("  $y")
//            }
//            println()
//            println()
//            println()

            currentlyFirstLook++
            println("curr $currentlyFirstLook")
        }


        val xSize = elves.maxOf { it.point.x } - elves.minOf { it.point.x } + 1
        val ySize = elves.maxOf { it.point.y } - elves.minOf { it.point.y }+ 1

        println("xSize $xSize ySize $ySize")

        return currentlyFirstLook.toLong()
//        return (xSize * ySize - elves.size).toLong()
    }

    fun part2(): Long {

        return -1
    }
}


//private val testInput ="""
//.....
//..##.
//..#..
//.....
//..##.
//.....
//""".trimIndent()
private val testInput =
    """
....#..
..###.#
#...#.#
.#...##
#.###..
##.#.##
.#..#..
""".trimIndent()
private val input =
    """
##...##......#.#.#..#####.##.#..#.#..#.###....##..#...#####...##.##..#
.#..##...##.....###.#.###..###.##.##.........###..#..#.#..##.#.#.#....
.###.#..#.#..##.##.#...##..#.#...###.#..##.#..#...#..####.##..##..#...
#..#..#.#....#.####......##.#.#.##..#.##.#.###..##.#...#..#..#.#..###.
.#.#....###.....###..##.###.#.###....##..#...#.####.#....#.#.###..##..
.##.#####...###..#...##...........#...##.#.###.#.....#...#...###.#....
.#.###.#..#.##.#...#....####...##..##.####..###.##......#..##..##...##
####..###.#..##...#..##.....###...######...##.....#..##..###.#.##.####
.#.##.###.#..#.#....######.#.####.##....#.#.#.#.###.###....#.######.#.
#####.#..####..######..##...#.####..####.....##..#.#.#.....##...#.###.
#..###.......##...#.###..#.##.####.....#...##.....#....#.#.##.#.##..#.
##..##.###.##.#....##.#.#..####..#.##.##..#.##....##.#.#..##.###...##.
.######..#..#..#####.###..####...#.#..#.##..####....#.###..#..#####...
#.#.###....##....##.#...#...###.##.##.##.##.#.#####.#####.......##.##.
....#.#.#.###.#...###.##.#..##.##..##.#.#....##.#.#.#####.#..##..###.#
.######...##..#..#...#..#.###.##.....#..##..#.##....#..####.##...##...
..#.#..#.###.#.#.##.#....##.###...#.#...##.#......#.######..##.#.#.#..
#...##..#..####.#.#...#.#.#...#....##..##..#.#..##.#..#.###....#.#...#
#..###...#.#.#.####.##..#.#..###.#..#...#..##..##.#....###.#...#.#..#.
.##....####.#.##.###.###..####..####.#.....##.#.#.####..##.##....####.
#.###.#...###....####......#...#..##...#.#.#..#.##.#.#.##..#....#...#.
##.#.#.####.##...###...#####......##..##.##.#..##..##...###..##.#.#.##
#.....#.##..#..##..#...###.###...#...#.#.##.....#..#.#.#.##.#..#.....#
#..#..###.#####..##.###.####...#.#####..##.....#.#.......#.#.#...###..
#.#.##.....###....#..#.###..###..#.#####..######..####.####...##..###.
#......#...#......#...##.#.##############.##......#..##.#.#.####.#.###
.####.#....#.###..##.#.##..######..#..#.###..#.......##.####.....#.#..
#..##..##.##..#..#..##...##.....##..#...##..#.###.#..###.##..#..#...##
###.##..#.#.####.####...#.#..#..##...###.....#...###..#..#...##..#.#..
#.##.#.###.....###.####.#.##.##.###.##..##.#..#.###...#....###.##..##.
##..###.#.##.##...##.##....#...#...#....###..#...#.#...#..##.#.#..#..#
##.#.###.#.#.######.#####.#..........##..#...##.#..#..#..##.##.#.#..##
.#..#####.##.##.#.#...#.##.....#..##.##..#.#..####.###..####.#..##..##
##.##.##.###.#..#.#..######.#.#.....#.#.#.#.#...#.##..#.#.###..#.#..#.
##...##.#...##.#..###..#..##......##...#............###.##.#.#.#.#.###
#..#..####..###.#.###.#.##..#####.##.####...##.##..#..##.#.###.#...###
.##.#.#.#...#..##..#...##...###.#...#.##.##..........##.##.###.#......
##..###.####.#....##.....#..##.#...#.##..#.#.#.##.#..#.#...#...##.##.#
#.#..#..#...##..#..###..#..#..#.#..#.#####.##..#...#..#..#..##.#.#.#..
.##..##..#..#.##.#..#.###..#.#...#######.#....#...####..##.#.##.#.#.##
.#####.....#....#.###...#########....#....#.##.#.##..####...#.##.#....
####..#..#.#....###.#...#.#.#####....#..#.###.#.#.#.##.########.##.#..
...#.######.#..##.##.#..##...###.#.#.#.#.#.#..#..###.####.##.##.#.##..
.#.#.##....###.#.#######.###...#.##.##.##.#..#######.#....##.....##..#
.#...##.#..#.##...#.####...#..#.##.#.##.#....##..##.###..##.##..#...#.
.###....#################..#.#.#..##.#.#..#.#.##..#.#.##.##.#....#..##
..#...#.#.###.#.#.#.####.#.##.#.#####..###..###....#####..##...##.#.##
.#...###..#.###..##.##.##.#......#.#..#.####.##.#.....#.....#.##.##.#.
###..##.#.##...##.#.....####.##.#.#.#...###..###.##.#..#####..#.#...##
.##.#.##...#.#...####.#.#..#...#.#.##..###.##..###.#.####..#.#..##..#.
####.##...###...##...########..#..##..#..#...#..#.#....##.#.#.#.##..#.
#..#...#.###..##.#..##..#.#.#.##.#..##.####..#.##.#.##..##...####..#..
...#.#........###.#####...###.#.###..#.....#####.####..########......#
.##..#....#.#...#..#####.#...###.#....#.##.##......##..#..#######....#
#..#####.##.##.##.##....#..#..#...#........##.#.#.##.##.####.###......
#.###..##.#....##....###..##.#.#.#.#####.##.##..##.##...####......###.
.##.....###.###..#####....##...##..#.#..#.#...##..#....#..#...#....##.
....##.##..##..#.#####.###.#..#.###.#..#..####..#.#.####...#.######.##
#.###...#.##.######.#..##.##.#..##...###.#.#..##...##....##..#...###.#
..#.##..#.#.###...####.#.#....##..##..##...#.##..#...#...#.....#..##.#
..##...#.#.#..#..###.###..#....##.#.#.#.#######.###.##....#..##.##.###
#......#######.#..#.######.##..#.#.###.###..###..##.####.....#....#.##
####..##..#.#....###.#..#...###..###.....######.##.#....####.#.#.####.
.##.###.###.###.###.#####.#.##.#.#..###..#.#..#.##...##.....##.#####.#
##..#.#.#.#.#.##.####.#.##...#.#.##......#....#.###.##....#...##..####
.######.#.###.#..........#######.#.#.##..#..##...#.####...#.##.....###
#..#..####.#.......#...#.#....####.#..#...#####..###.########..#...##.
#.....##.####.##.#.##.#..#.#.#.#..#.#...##.##.#.##.#..#..##..#.#.#.##.
..#....###.##.###.#..###.#.####.####.#.##...#######.##..####.##.......
.#.....#####...####..#.#..#..###.#...#..##...#..#..###.#.####...###.##
""".trimIndent()


/**

 */