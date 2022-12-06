package y2015

fun main(args: Array<String>) {
    part2()
}

private fun part1() {

    fun race(speed: Int, timeAtSpeed: Int, rest: Int, time: Int): Int {
        var elapsed = 0
        var isResting = false
        var distance = 0

        var speedCounter = timeAtSpeed
        var restCounter = rest

        while (elapsed < time) {
            if (isResting) {
                restCounter--
                if (restCounter == 0) {
                    restCounter = rest
                    isResting = false
                }
            } else {
                speedCounter--
                distance += speed
                if (speedCounter == 0) {
                    speedCounter = timeAtSpeed
                    isResting = true
                }
            }
            elapsed++
        }

        return distance
    }

    input.lines().map { it.split(" ") }.forEach { line ->
        val name = line[0]
        val speed = line[3].toInt()
        val speedTime = line[6].toInt()
        val restTime = line[13].toInt()

        val distance = race(speed, speedTime, restTime, 2503)
        println("$name went $distance")
    }
}

private fun part2() {

    data class Racer(
        val name: String,
        val speed: Int,
        val speedTime: Int,
        val restTime: Int,
        var points: Int = 0,
        var isResting: Boolean = false,
        var distance: Int = 0,
        var speedCounter: Int = speedTime,
        var restCounter: Int = restTime,
    )

    fun step(racer: Racer): Racer {
        if (racer.isResting) {
            racer.restCounter--
            if (racer.restCounter == 0) {
                racer.restCounter = racer.restTime
                racer.isResting = false
            }
        } else {
            racer.speedCounter--
            racer.distance += racer.speed
            if (racer.speedCounter == 0) {
                racer.speedCounter = racer.speedTime
                racer.isResting = true
            }
        }
        return racer

    }


    val racers = input.lines().map { it.split(" ") }.map { line ->
        val name = line[0]
        val speed = line[3].toInt()
        val speedTime = line[6].toInt()
        val restTime = line[13].toInt()

        Racer(name, speed, speedTime, restTime)
//        val distance = race(speed, speedTime, restTime, 2503)
//        println("$name went $distance")
    }

    repeat(2503) {
        val maxDistance = racers.map {
            step(it)
        }.maxOf { it.distance }

        racers.filter { it.distance == maxDistance }.forEach {
            it.points++
        }
    }

    racers.maxBy { it.points }.also(::println)

}

private val testInput =
    """
Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
""".trimIndent()
private val input =
    """
Vixen can fly 19 km/s for 7 seconds, but then must rest for 124 seconds.
Rudolph can fly 3 km/s for 15 seconds, but then must rest for 28 seconds.
Donner can fly 19 km/s for 9 seconds, but then must rest for 164 seconds.
Blitzen can fly 19 km/s for 9 seconds, but then must rest for 158 seconds.
Comet can fly 13 km/s for 7 seconds, but then must rest for 82 seconds.
Cupid can fly 25 km/s for 6 seconds, but then must rest for 145 seconds.
Dasher can fly 14 km/s for 3 seconds, but then must rest for 38 seconds.
Dancer can fly 3 km/s for 16 seconds, but then must rest for 37 seconds.
Prancer can fly 25 km/s for 6 seconds, but then must rest for 143 seconds.
""".trimIndent()
