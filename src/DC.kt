import kotlin.math.abs

data class C(val x: Int, val y: Int) {
    override fun toString(): String {
        return "($x;$y)"
    }

    fun manhattan(other: C): Int {
        return abs(x - other.x) + abs(y - other.y)
    }

    fun shiftY(diff: Int): C = C(x, y + diff)
    fun shiftX(diff: Int): C = C(x + diff, y)
}
