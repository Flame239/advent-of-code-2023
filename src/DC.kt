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
