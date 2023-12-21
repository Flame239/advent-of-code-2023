import kotlin.math.abs

data class C(val i: Long, val j: Long) {
    override fun toString(): String {
        return "($i;$j)"
    }

    fun manhattan(other: C): Long {
        return abs(i - other.i) + abs(j - other.j)
    }

    fun shiftY(diff: Int): C = C(i, j + diff)
    fun shiftX(diff: Int): C = C(i + diff, j)
}

data class Dir(val stepI: Int, val stepJ: Int) {
    val horizontal = stepJ != 0
    val vertical = stepI != 0
}

val DIR_UP = Dir(-1, 0)
val DIR_DOWN = Dir(1, 0)
val DIR_LEFT = Dir(0, -1)
val DIR_RIGHT = Dir(0, 1)
