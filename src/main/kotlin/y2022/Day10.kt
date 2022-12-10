package y2022

fun main(args: Array<String>) {

    val testInput = Template(testInput)
    testInput.part1()
        .also { println("Part1 test $it") }
        .also { check(it == "13140") }
    testInput.part2()
        .also { println("Part2 test $it") }
//        .also { check(it == """
//            ##..##..##..##..##..##..##..##..##..##..
//            ###...###...###...###...###...###...###
//            .####....####....####....####....####....
//            #####.....#####.....#####.....#####....
//            .######......######......######......###
//            ########.......#######.......#######.....
//        """.trimIndent()) }

    val realInput = Template(input)
    realInput.part1()
        .also { println("Part1 real $it") }
        .also { check(it == "14360") }
    realInput.part2()
        .also { println("Part2 real $it") }
//        .also { check(it == """
//###...##..#..#..##..####.###..####.#####
//#..#.#..#.#.#..#..#.#....#..#.#.......##
//###..#....##...#..#.###..#..#.###....#.#
//#..#.#.##.#.#..####.#....###..#.....#..#
//#..#.#..#.#.#..#..#.#....#.#..#....#...#
//###...###.#..#.#..#.####.#..#.####.####.
//""".trim()) } // BGKAEREZ
}

class Template(private val input: String) {

    fun part1(): String {

        var cycle = 1
        var values = 1
        var prevValues = values
        var neededSum = 0
        val neededValuesComplete = mutableListOf<Int>()
        input.lines().map { it.split(" ") }.forEach { line ->


            val neededCycle = 20
            fun includeNeededValues(neededCycle: Int) {
                if (!neededValuesComplete.contains(neededCycle)) {
                    if (cycle == neededCycle) {
                        println("cycle $cycle value is $values")
                        neededSum += values * neededCycle
                        neededValuesComplete.add(neededCycle)
                    } else if (cycle == neededCycle + 1) {
                        println("cycle $cycle value is $prevValues")
                        neededSum += prevValues * neededCycle
                        neededValuesComplete.add(neededCycle)
                    }
                }
            }
            includeNeededValues(20)
            includeNeededValues(60)
            includeNeededValues(100)
            includeNeededValues(140)
            includeNeededValues(180)
            includeNeededValues(220)


            prevValues = values

            if (line.first() == "addx") {
                cycle += 2
                val toInt = line.last().toInt()
                if (cycle >= 219) {
                    println("cycle $cycle adding $toInt to $values")
                }
//                println("adding $toInt")
                values += toInt
            } else {
                cycle++
            }

        }
        return neededSum.toString()
    }

    fun part2(): String {
        var cycle = 1
        var spritePosition = 1
        var prevSpritePosition = spritePosition
        val output = StringBuilder()
        output.appendLine()
        input.lines().map { it.split(" ") }.forEach { line ->

            val cycleMod = cycle % 40
            if ( cycleMod in (spritePosition..(spritePosition+2))) {
                output.append("#")
            } else {
                output.append(" ")
            }
            if (cycleMod == 0) {
                output.appendLine()
            }
            if (line.first() == "addx") {
                if ((cycle+1) % 40  == 0) {
                    output.appendLine()
                }
                if ( (cycleMod+1) in (spritePosition..(spritePosition+2))) {
                    output.append("#")
                } else {
                    output.append(" ")
                }
            }


            prevSpritePosition = spritePosition

            if (line.first() == "addx") {
                cycle += 2
                val toInt = line.last().toInt()

//                println("adding $toInt")
                spritePosition += toInt
            } else {
                cycle++
            }

        }
        return output.toString()
    }
}


private val testInput =
    """
addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop
""".trimIndent()
private val input =
    """
noop
noop
addx 6
addx -1
noop
addx 5
addx 3
noop
addx 3
addx -1
addx -13
addx 17
addx 3
addx 3
noop
noop
noop
addx 5
addx 1
noop
addx 4
addx 1
noop
addx -38
addx 5
noop
addx 2
addx 3
noop
addx 2
addx 2
addx 3
addx -2
addx 5
addx 2
addx -18
addx 6
addx 15
addx 5
addx 2
addx -22
noop
noop
addx 30
noop
noop
addx -39
addx 1
addx 19
addx -16
addx 35
addx -28
addx -1
addx 12
addx -8
noop
addx 3
addx 4
noop
addx -3
addx 6
addx 5
addx 2
noop
noop
noop
noop
noop
addx 7
addx -39
noop
noop
addx 5
addx 2
addx 2
addx -1
addx 2
addx 2
addx 5
addx 1
noop
addx 4
addx -13
addx 18
noop
noop
noop
addx 12
addx -9
addx 8
noop
noop
addx -2
addx -36
noop
noop
addx 5
addx 2
addx 3
addx -2
addx 2
addx 2
noop
addx 3
addx 5
addx 2
addx 19
addx -14
noop
addx 2
addx 3
noop
addx -29
addx 34
noop
addx -35
noop
addx -2
addx 2
noop
addx 6
noop
noop
noop
noop
addx 2
noop
addx 3
addx 2
addx 5
addx 2
addx 1
noop
addx 4
addx -17
addx 18
addx 4
noop
addx 1
addx 4
noop
addx 1
noop
noop
""".trimIndent()


/**

 */