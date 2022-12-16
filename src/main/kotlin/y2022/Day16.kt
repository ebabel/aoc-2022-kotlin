package y2022

import alsoPrintOnLines

fun main(args: Array<String>) {

    val testInput = Day16(testInput)
//    testInput.part1()
//        .also { println("Part1 test $it") }
//        .also { check(it == "result") }
//    testInput.part2()
//        .also { println("Part2 test $it") }
//        .also { check(it == "result") }
//
    val realInput = Day16(input)
    realInput.part1()
        .also { println("Part1 real $it") }
        .also { check(it == "result") }
//    realInput.part2()
//        .also { println("Part2 real $it") }
//        .also { check(it == "result") }
}

sealed class Action {
    data class Move(val valve: Day16.Valve) : Action()
    object Open : Action()
    object Initial : Action()
}
class Day16(private val input: String) {

    data class Valve(val name: String, val flowRate: Int) {
        val tunnels: MutableList<Valve> = mutableListOf()
    }
    fun part1(): String {
        val valves = input.lines().map {
            val name = it.substring(6,8)
            val flowRate = it.substringAfter("=").substringBefore(";").toInt()
            Valve(name, flowRate)
        }.alsoPrintOnLines()
        input.lines().forEach { line ->
            val substringAfter = if (line.contains("valves")) {
                line.substringAfter("valves ")
            } else {
                line.substringAfter("valve ")
            }
            val tunnels = substringAfter.split(", ").map { tunnel ->
//                println("tunnel $tunnel")
                valves.first { it.name == tunnel }
            }
            valves.first { it.name == line.substring(6,8) }.tunnels.addAll(tunnels)
        }

        var maxPressure: Int = 0
        var maxPressurePath: List<Valve> = emptyList()

        var tries = 0L
        fun hurry(
            valve: Valve,
            path: List<Action>,
            openValves: List<Valve> = emptyList(),
            minutes: Int = 1,
            pressureReleased: Int = 0
        ) {
            tries++
            val newPressure = openValves.sumOf { it.flowRate } + pressureReleased

            if (minutes == 30) {
                if (newPressure > maxPressure) {
                    maxPressure = newPressure
                    println("Done: pressure: $newPressure $tries")
                }
            } else if (openValves.size == valves.size) {
                hurry(valve, path, openValves, minutes + 1, newPressure)
            } else {
                val last = path.last()
                if (valve in openValves) {
                    if (last is Action.Move) {
                        valve.tunnels.filter { it != last.valve }.forEach {
                            hurry(it, path.plus(Action.Move(valve)), openValves, minutes + 1, newPressure)
                        }
                    } else {
                        valve.tunnels.forEach {
                            hurry(it, path.plus(Action.Move(valve)), openValves, minutes + 1, newPressure)
                        }
                    }
                } else {
                    // open valve
                    hurry(valve, path.plus(Action.Open), openValves.plus(valve), minutes + 1, newPressure)

                    // but also don't open valve
                    if (last is Action.Move) {
                        valve.tunnels.filter { it != last.valve }.forEach {
                            hurry(it, path.plus(Action.Move(valve)), openValves, minutes + 1, newPressure)
                        }
                    } else {
                        valve.tunnels.forEach {
                            hurry(it, path.plus(Action.Move(valve)), openValves, minutes + 1, newPressure)
                        }
                    }
                }
            }

        }

        hurry(valves[0], listOf(Action.Initial), openValves = valves.filter { it.flowRate == 0 })

//        data class Trip(val cost: Int, val combinedFlow: Int)
//        val destMap = mutableMapOf<Pair<Valve, Valve>, Trip>()
//        valves.filter { it.flowRate > 0 }.forEach {valve ->
//            valve.tunnels.forEach { tunnel ->
//                val pair = if (valve.name < tunnel.name) {
//                    valve to tunnel
//                } else {
//                    tunnel to valve
//                }
//                val max = maxOf(valve.name, tunnel.name)
//                destMap[pair] = Trip(1, valve.flowRate + tunnel.flowRate)
//            }
//        }
//
//
//        destMap.toMutableMap().forEach { destMapEntry ->
//            destMapEntry.key.second.tunnels.forEach {tunnel ->
//                val valve = destMapEntry.key.first
//                if (tunnel != valve) {
//                    val pair = if (valve.name < tunnel.name) {
//                        valve to tunnel
//                    } else {
//                        tunnel to valve
//                    }
//                    val max = maxOf(valve.name, tunnel.name)
//                    if (!destMap.containsKey(pair)) {
//                        destMap[pair] = Trip(2, destMapEntry.value.combinedFlow + tunnel.flowRate)
//                    }
//                }
//            }
//
//        }
//
//        destMap.toList().alsoPrintOnLines()

        return "result"
    }

    fun part2(): String {

        return "result"
    }
}



private val testInput =
"""
Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II
""".trimIndent()
private val input =
"""
Valve AA has flow rate=0; tunnels lead to valves BJ, CF, DX, RB, AQ
Valve EK has flow rate=12; tunnels lead to valves JE, VE, PJ, CS, IX
Valve HT has flow rate=4; tunnels lead to valves DZ, GA, CI, DE, JS
Valve MS has flow rate=11; tunnels lead to valves PJ, WG, CA, YE
Valve VW has flow rate=13; tunnels lead to valves DH, IX
Valve TQ has flow rate=14; tunnels lead to valves WG, KI, JE, UH, XT
Valve WI has flow rate=3; tunnels lead to valves XF, DX, DE, EW
Valve UW has flow rate=6; tunnels lead to valves XT, CD, NZ, JS
Valve EG has flow rate=23; tunnels lead to valves AY, GW
Valve AP has flow rate=7; tunnels lead to valves IZ, VQ, TB, SB
Valve WY has flow rate=16; tunnel leads to valve VQ
Valve FX has flow rate=20; tunnels lead to valves DH, OO
Valve KS has flow rate=19; tunnels lead to valves NC, HJ
Valve TG has flow rate=17; tunnels lead to valves NC, FY
Valve KR has flow rate=18; tunnels lead to valves MN, DN, YW, AY
Valve FG has flow rate=8; tunnels lead to valves NU, RR, MT, MK, DZ
Valve FY has flow rate=0; tunnels lead to valves TG, CD
Valve NU has flow rate=0; tunnels lead to valves FG, HJ
Valve AY has flow rate=0; tunnels lead to valves EG, KR
Valve DH has flow rate=0; tunnels lead to valves FX, VW
Valve IX has flow rate=0; tunnels lead to valves VW, EK
Valve DZ has flow rate=0; tunnels lead to valves HT, FG
Valve YE has flow rate=0; tunnels lead to valves CI, MS
Valve OO has flow rate=0; tunnels lead to valves FX, CS
Valve SB has flow rate=0; tunnels lead to valves RR, AP
Valve CD has flow rate=0; tunnels lead to valves UW, FY
Valve IZ has flow rate=0; tunnels lead to valves XF, AP
Valve JE has flow rate=0; tunnels lead to valves EK, TQ
Valve DN has flow rate=0; tunnels lead to valves KR, VE
Valve UH has flow rate=0; tunnels lead to valves MN, TQ
Valve TB has flow rate=0; tunnels lead to valves AP, BJ
Valve XT has flow rate=0; tunnels lead to valves TQ, UW
Valve RR has flow rate=0; tunnels lead to valves FG, SB
Valve BJ has flow rate=0; tunnels lead to valves TB, AA
Valve DE has flow rate=0; tunnels lead to valves HT, WI
Valve MT has flow rate=0; tunnels lead to valves EW, FG
Valve HJ has flow rate=0; tunnels lead to valves KS, NU
Valve KI has flow rate=0; tunnels lead to valves GW, TQ
Valve JS has flow rate=0; tunnels lead to valves UW, HT
Valve XF has flow rate=0; tunnels lead to valves WI, IZ
Valve VE has flow rate=0; tunnels lead to valves DN, EK
Valve CI has flow rate=0; tunnels lead to valves YE, HT
Valve GW has flow rate=0; tunnels lead to valves EG, KI
Valve EW has flow rate=0; tunnels lead to valves MT, WI
Valve MN has flow rate=0; tunnels lead to valves KR, UH
Valve RB has flow rate=0; tunnels lead to valves NZ, AA
Valve AQ has flow rate=0; tunnels lead to valves AA, MK
Valve WG has flow rate=0; tunnels lead to valves TQ, MS
Valve YW has flow rate=0; tunnels lead to valves CA, KR
Valve CA has flow rate=0; tunnels lead to valves YW, MS
Valve PJ has flow rate=0; tunnels lead to valves MS, EK
Valve NC has flow rate=0; tunnels lead to valves TG, KS
Valve CF has flow rate=0; tunnels lead to valves GA, AA
Valve NZ has flow rate=0; tunnels lead to valves RB, UW
Valve VQ has flow rate=0; tunnels lead to valves WY, AP
Valve GA has flow rate=0; tunnels lead to valves CF, HT
Valve CS has flow rate=0; tunnels lead to valves OO, EK
Valve MK has flow rate=0; tunnels lead to valves AQ, FG
Valve DX has flow rate=0; tunnels lead to valves AA, WI
""".trimIndent()


/**

 */