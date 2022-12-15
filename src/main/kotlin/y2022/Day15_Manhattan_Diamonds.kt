package y2022

import Point
import kotlin.math.abs

fun main(args: Array<String>) {

    val testInput = Day15(testInput)
    testInput.part1(10)
        .also { println("Part1 test $it") }
        .also { check(it == "24") }

    val realInput = Day15(input)
    realInput.part1(2000000)
        .also { println("Part1 real $it") }
        .also { check(it == "5832528") }
    realInput.part2(4000000)
        .also { println("Part2 real $it") }
        .also { check(it == "result") }
}

class Day15(private val input: String) {

    fun part1(interestingY: Int): String {

        val sensorBeacons = generateBeacons()
        val workingRangesOnInterestingY: MutableList<IntRange> = rangesOnInterestingY(sensorBeacons, interestingY)

        return (workingRangesOnInterestingY.first().last - workingRangesOnInterestingY.first().first).toString()
    }

    private fun rangesOnInterestingY(
        sensorBeacons: MutableMap<Point, Point>,
        interestingY: Int
    ): MutableList<IntRange> {
        val rangesOnInterestingY: MutableSet<IntRange> = mutableSetOf()

        for ((sensor, beacon) in sensorBeacons) {
            val dist = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)
            if (sensor.y + dist >= interestingY && sensor.y - dist <= interestingY) {
                val spread = if (sensor.y > interestingY) {
                    abs(sensor.y - interestingY - dist)
                } else {
                    abs(interestingY - sensor.y - dist)
                }
                rangesOnInterestingY.add((sensor.x - spread)..(sensor.x + spread))
            }
        }

        val workingRangesOnInterestingY: MutableList<IntRange> = mutableListOf()
        rangesOnInterestingY.sortedBy { it.last }.sortedBy { it.first }.forEachIndexed { index, range ->
            if (workingRangesOnInterestingY.isEmpty()) {
                workingRangesOnInterestingY.add(range)
            } else {
                if (workingRangesOnInterestingY.last().last >= range.first) {
                    val rangeRemoved = workingRangesOnInterestingY.removeLast()
                    workingRangesOnInterestingY.add(rangeRemoved.first..(maxOf(range.last, rangeRemoved.last)))
                } else {
                    workingRangesOnInterestingY.add(range)
                }

            }
        }
        return workingRangesOnInterestingY
    }

    private fun generateBeacons(): MutableMap<Point, Point> {
        val sensorBeacons = mutableMapOf<Point, Point>()

        input.lines().forEach { s ->
            val words = s.split(" ")
            val sX = words[2].substringAfter("=").dropLast(1).toInt()
            val sY = words[3].substringAfter("=").dropLast(1).toInt()
            val bX = words[8].substringAfter("=").dropLast(1).toInt()
            val bY = words[9].substringAfter("=").toInt()

            sensorBeacons[Point(sX, sY)] = Point(bX, bY)
        }
        return sensorBeacons
    }

    fun part2(xAndYMost: Int): String {
        val sensorBeacons = generateBeacons()

        (0..xAndYMost).forEach { i ->
            val ranges = rangesOnInterestingY(sensorBeacons, i)
            if (ranges.size > 1) {
                return ((ranges.first().last + 1) * 4000000L + i).toString()
            }
        }
        return "result"
    }
}



private val testInput =
"""
Sensor at x=2, y=18: closest beacon is at x=-2, y=15
Sensor at x=9, y=16: closest beacon is at x=10, y=16
Sensor at x=13, y=2: closest beacon is at x=15, y=3
Sensor at x=12, y=14: closest beacon is at x=10, y=16
Sensor at x=10, y=20: closest beacon is at x=10, y=16
Sensor at x=14, y=17: closest beacon is at x=10, y=16
Sensor at x=8, y=7: closest beacon is at x=2, y=10
Sensor at x=2, y=0: closest beacon is at x=2, y=10
Sensor at x=0, y=11: closest beacon is at x=2, y=10
Sensor at x=20, y=14: closest beacon is at x=25, y=17
Sensor at x=17, y=20: closest beacon is at x=21, y=22
Sensor at x=16, y=7: closest beacon is at x=15, y=3
Sensor at x=14, y=3: closest beacon is at x=15, y=3
Sensor at x=20, y=1: closest beacon is at x=15, y=3
""".trimIndent()
private val input =
"""
Sensor at x=193758, y=2220950: closest beacon is at x=652350, y=2000000
Sensor at x=3395706, y=3633894: closest beacon is at x=3404471, y=3632467
Sensor at x=3896022, y=3773818: closest beacon is at x=3404471, y=3632467
Sensor at x=1442554, y=1608100: closest beacon is at x=652350, y=2000000
Sensor at x=803094, y=813675: closest beacon is at x=571163, y=397470
Sensor at x=3491072, y=3408908: closest beacon is at x=3404471, y=3632467
Sensor at x=1405010, y=486446: closest beacon is at x=571163, y=397470
Sensor at x=3369963, y=3641076: closest beacon is at x=3404471, y=3632467
Sensor at x=3778742, y=2914974: closest beacon is at x=4229371, y=3237483
Sensor at x=1024246, y=3626229: closest beacon is at x=2645627, y=3363491
Sensor at x=3937091, y=2143160: closest beacon is at x=4229371, y=3237483
Sensor at x=2546325, y=2012887: closest beacon is at x=2645627, y=3363491
Sensor at x=3505386, y=3962087: closest beacon is at x=3404471, y=3632467
Sensor at x=819467, y=239010: closest beacon is at x=571163, y=397470
Sensor at x=2650614, y=595151: closest beacon is at x=3367919, y=-1258
Sensor at x=3502942, y=6438: closest beacon is at x=3367919, y=-1258
Sensor at x=3924022, y=634379: closest beacon is at x=3367919, y=-1258
Sensor at x=2935977, y=2838245: closest beacon is at x=2645627, y=3363491
Sensor at x=1897626, y=7510: closest beacon is at x=3367919, y=-1258
Sensor at x=151527, y=640680: closest beacon is at x=571163, y=397470
Sensor at x=433246, y=1337298: closest beacon is at x=652350, y=2000000
Sensor at x=2852855, y=3976978: closest beacon is at x=3282750, y=3686146
Sensor at x=3328398, y=3645875: closest beacon is at x=3282750, y=3686146
Sensor at x=3138934, y=3439134: closest beacon is at x=3282750, y=3686146
Sensor at x=178, y=2765639: closest beacon is at x=652350, y=2000000
Sensor at x=3386231, y=3635056: closest beacon is at x=3404471, y=3632467
Sensor at x=3328074, y=1273456: closest beacon is at x=3367919, y=-1258
Sensor at x=268657, y=162438: closest beacon is at x=571163, y=397470
""".trimIndent()


/**

 */