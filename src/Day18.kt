import kotlin.math.absoluteValue

fun main() {
    val letterToDir = mapOf("R" to DIR_RIGHT, "U" to DIR_UP, "L" to DIR_LEFT, "D" to DIR_DOWN)
    val hexToDir = mapOf('0' to DIR_RIGHT, '3' to DIR_UP, '2' to DIR_LEFT, '1' to DIR_DOWN)

    data class Dig(val dir: Dir, val len: Long)
    data class DigInput(val dir: String, val len: Long, val color: String) {
        fun normalDig() = Dig(letterToDir[dir]!!, len)
        fun hexDig() = Dig(hexToDir[color[5]]!!, color.substring(0, 5).toLong(16))
    }

    data class V2(val x: Long, val y: Long) {
        fun shift(dir: Dir, len: Long) = V2(x + len * dir.stepJ, y + len * dir.stepI)
    }

    val planInput = readInput("Day18").map { l -> l.split(" ").let { DigInput(it[0], it[1].toLong(), it[2].substring(2, 8)) } }


    fun shoelace(coords: List<V2>): Long = coords
            .zipWithNext { c1, c2 ->
                c1.x * c2.y - c1.y * c2.x
            }.sum().absoluteValue / 2

    fun interiorPoints(area: Long, border: Long): Long {
        // Pick's theorem: Area = inside + border / 2 - 1
        // we need: inside + border which is = Area + border / 2 + 1
        // where Area could be calculated using shoelace formula
        return area + border / 2 + 1
    }

    fun volume(plan: List<Dig>): Long {
        val coords = plan.scan(V2(0, 0)) { prev, d -> prev.shift(d.dir, d.len) }
        val area = shoelace(coords)
        val border = plan.sumOf(Dig::len)
        return interiorPoints(area, border)
    }

    fun part1(): Long = volume(planInput.map(DigInput::normalDig))
    fun part2(): Long = volume(planInput.map(DigInput::hexDig))

    measure { part1() }
    measure { part2() }
}
