import kotlin.math.max
import kotlin.math.min

fun main() {
    data class V3(val x: Int, val y: Int, var z: Int)
    data class Brick(val id: Int, val start: V3, val end: V3) {
        val minZ: Int
            get() = min(start.z, end.z)
        val maxZ: Int
            get() = max(start.z, end.z)

        fun shiftZto(targetZ: Int) {
            val zDiff = minZ - targetZ
            start.z = minZ - zDiff
            end.z = maxZ - zDiff
        }
    }

    val bricks = readInput("Day22").mapIndexed { i, l ->
        val coords = l.split("~").map { b -> b.split(",").map(String::toInt).let { V3(it[0], it[1], it[2]) } }
        Brick(i, coords[0], coords[1])
    }

    val bb = Array(10) { Array(10) { ArrayList<Brick>() } }
    bricks.sortedBy(Brick::minZ).forEach { b ->
        var newMinZ = 0
        for (x in b.start.x..b.end.x) for (y in b.start.y..b.end.y) {
            newMinZ = max(newMinZ, (bb[x][y].lastOrNull()?.maxZ ?: 0) + 1)
            bb[x][y].add(b)
        }
        b.shiftZto(newMinZ)
    }

    val supports = Array(bricks.size) { HashSet<Int>() }
    val supportedBy = Array(bricks.size) { HashSet<Int>() }

    for (x in bb.indices) for (y in bb[0].indices) {
        bb[x][y].windowed(2).forEach { (bot, top) ->
            if (top.minZ == bot.maxZ + 1) {
                supports[bot.id].add(top.id)
                supportedBy[top.id].add(bot.id)
            }
        }
    }

    fun part1(): Int = supports.count { top ->
        // either no bricks on top or all the bricks on top supported by any other bricks
        top.isEmpty() || top.all { t -> supportedBy[t].size > 1 }
    }

    fun part2(): Int = bricks.indices.sumOf { i ->
        val willMove = BooleanArray(bricks.size)
        val bq = ArrayDeque<Int>()
        bq.add(i)
        willMove[i] = true
        while (!bq.isEmpty()) {
            val bi = bq.removeFirst()
            // top brick will move if all its bottom bricks will move
            val topMove = supports[bi].filter { t -> supportedBy[t].all { bot -> willMove[bot] } }
            topMove.forEach { t -> willMove[t] = true }
            bq.addAll(topMove)
        }
        willMove.count { it } - 1
    }

    measure { part1() }
    measure { part2() }
}
