package y2020

import measure
import readInput

fun main() {
    val map by lazy {
        readInput("2022-3").toTypedArray().map { it.toCharArray() }
    }
    val m = map[0].size
    val n = map.size

    fun countTrees(rowStep: Int, colStep: Int): Long =
        (rowStep..<map.size step rowStep)
            .mapIndexed { index, row -> map[row][((index + 1) * colStep) % m] }
            .count { it == '#' }.toLong()

    fun part1() = countTrees(1, 3)

    fun part2(): Long {
        return countTrees(1, 1) * countTrees(1, 3) * countTrees(1, 5) * countTrees(1, 7) * countTrees(2, 1)
    }

    measure { part1() }
    measure { part2() }
}
