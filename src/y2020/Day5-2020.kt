package y2020

import measure
import readInput

fun main() {
    val charToBit = mapOf(
        'B' to '1',
        'F' to '0',
        'R' to '1',
        'L' to '0'
    )

    fun toSeatId(boardingPass: String): Int = boardingPass.map { c -> charToBit[c]!! }.joinToString("").toInt(2)

    val passes by lazy {
        readInput("2020-5").map { toSeatId(it) }.toHashSet()
    }

    fun part1(): Int = passes.max()

    fun part2(): Int = (passes.min()..passes.max()).first { !passes.contains(it) }

    measure { part1() }
    measure { part2() }
}
