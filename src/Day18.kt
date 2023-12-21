import kotlin.math.abs

fun main() {
    data class Dig(val dir: Dir, val len: Int, val color: String)
    data class Coord(val x: Int, val y: Int)

    val dirLetters = mapOf("R" to DIR_RIGHT, "U" to DIR_UP, "L" to DIR_LEFT, "D" to DIR_DOWN)

    val plan = readInput("Day18-ex").map { l -> l.split(" ").let { Dig(dirLetters[it[0]]!!, it[1].toInt(), it[2]) } }

    fun shoelace(coords: List<Coord>): Int {
        val n = coords.size
        var area = 0

        var j: Int = n - 1
        for (i in 0 until n) {
            area += (coords[j].x + coords[i].x) * (coords[j].y - coords[i].y)
            j = i
        }

        return abs(area / 2)
    }

//    fun area(coords: List<Coord>): Int {
//        val byY: Map<Int, List<Coord>> = coords.groupBy { it.y }
//        return byY.toList().sumOf { (y, pts) -> pts.sortedBy { it.x }.chunked(2).sumOf { it[1].x - it[0].x + 1 }.also { println("$y $it") } }
//    }

    fun part1(): Int {
        var cur = Coord(0, 0)
        val coords = mutableListOf<Coord>()
        plan.forEach { d ->
            cur = Coord(cur.x + d.len * d.dir.stepJ, cur.y + d.len * d.dir.stepI)
            coords.add(cur)
        }
        coords.println()

        coords.size.println()
        shoelace(coords).println()
        return -1
    }

    fun part2(): Int {
        return plan.size
    }

    shoelace(mutableListOf(Coord(0, 0), Coord(2, 0), Coord(2, 2), Coord(0, 2))).println()
    measure { part1() }
    measure { part2() }
}
