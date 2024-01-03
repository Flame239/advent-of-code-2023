fun main() {
    data class V3(val x: Int, val y: Int, val z: Int)
    data class Brick(val id: Int, val start: V3, val end: V3) {
        val x = start.x != end.x
        val y = start.y != end.y
        val z = start.z != end.z
    }

    val bricks = readInput("Day22-ex").mapIndexed { i, l ->
        val coords = l.split("~")
        val start = coords[0].split(",").map(String::toInt).let { V3(it[0], it[1], it[2]) }
        val end = coords[1].split(",").map(String::toInt).let { V3(it[0], it[1], it[2]) }
        Brick(i, start, end)
    }

    fun part1(): Int {
        val xy = Array(10) { Array(10) { ArrayList<Brick>() } }
        bricks.forEach { b ->
            if (b.x) {
                for (x in b.start.x..b.end.x) {
                    xy[x][b.start.y].add(b)
                }
            } else if (b.y) {
                for (y in b.start.y..b.end.y) {
                    xy[b.start.x][y].add(b)
                }
            } else {
                xy[b.start.x][b.start.y].add(b)
            }
        }

        val newZ = IntArray(bricks.size) { i -> bricks[i].start.z }
        val supports = Array(bricks.size) { HashSet<Int>() }

        for (x in xy.indices) for (y in xy[0].indices) {
            xy[x][y].sortedBy(Brick::z).windowed(2).forEach { (bottomB, topB) ->
                supports[bottomB.id].add(topB.id)
            }
        }

        supports.forEachIndexed { i, bs ->
            println("$i supports $bs")
        }
        return bricks.size
    }

    fun part2(): Int {
        return bricks.size
    }

    measure { part1() }
    measure { part2() }
}
