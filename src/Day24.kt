import kotlin.math.sign

fun main() {
    val range = 200000000000000.0..400000000000000.0

    data class V3(val x: Long, val y: Long, val z: Long)
    data class Hail(val pos: V3, val v: V3)

    val hails = readInput("Day24").map { l ->
        val s = l.split(Regex("\\s+@\\s+"))
        val reg = Regex(",\\s+")
        val (x0, y0, z0) = s[0].split(reg).map(String::toLong)
        val (vx, vy, vz) = s[1].split(reg).map(String::toLong)
        Hail(V3(x0, y0, z0), V3(vx, vy, vz))
    }

    fun intersectWithinRange(h1: Hail, h2: Hail): Boolean {
        // vx == 0?
        val k1 = h1.v.y.toDouble() / h1.v.x
        val b1 = h1.pos.y - k1 * h1.pos.x

        val k2 = h2.v.y.toDouble() / h2.v.x
        val b2 = h2.pos.y - k2 * h2.pos.x

        if (k1 == k2) {
            return false
        }

        val x = (b2 - b1) / (k1 - k2)
        val y = k1 * x + b1

        if (x !in range || y !in range) return false

        val h1Future = sign(x - h1.pos.x) == sign(h1.v.x.toDouble()) && sign(y - h1.pos.y) == sign(h1.v.y.toDouble())
        val h2Future = sign(x - h2.pos.x) == sign(h2.v.x.toDouble()) && sign(y - h2.pos.y) == sign(h2.v.y.toDouble())

        return h1Future && h2Future
    }

    fun part1(): Int {
        return hails.unorderedPairs().count { (h1, h2) -> intersectWithinRange(h1, h2) }
    }

    fun part2(): Int {
        return hails.size
    }

    measure { part1() }
    measure { part2() }
}
