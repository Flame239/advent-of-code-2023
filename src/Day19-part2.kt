import kotlin.math.max
import kotlin.math.min

fun main() {
    data class PartRange(val propRange: HashMap<String, IntRange>) {
        operator fun get(propName: String) = propRange[propName]!!
        operator fun set(propName: String, range: IntRange) {
            propRange[propName] = range
        }

        fun combinations(): Long = propRange.entries.fold(1L) { acc, (_, range) -> acc * range.count() }
        fun withUpdated(propName: String, range: IntRange): PartRange {
            val newRange = HashMap(propRange)
            newRange[propName] = range
            return PartRange(newRange)
        }
    }

    data class Workflow(val name: String, val rules: List<String>)

    val workflowsS = readInput("Day19").splitBy { it.isEmpty() }[0]
    val workflows: Map<String, Workflow> = workflowsS.map { w ->
        val name = w.substringBefore("{")
        val rules = w.substringAfter("{").substringBefore("}").split(',')
        Workflow(name, rules)
    }.associateBy(Workflow::name)

    var total = 0L
    fun go(wName: String, range: PartRange) {
        if (wName == "R") return
        if (wName == "A") {
            total += range.combinations()
            return
        }
        val w = workflows[wName]!!
        var curRange = range
        for (r in w.rules) {
            if (r.contains('>')) {
                val (prop, valueS) = r.substringBefore(':').split('>')
                val dst = r.substringAfter(':')
                val value = valueS.toInt()

                val propRange = curRange[prop]
                if (value < propRange.last) {
                    val okRange = curRange.withUpdated(prop, IntRange(max(value + 1, propRange.first), propRange.last))
                    go(dst, okRange)
                }

                if (value >= propRange.first) {
                    curRange = curRange.withUpdated(prop, IntRange(propRange.first, min(value, propRange.last)))
                } else {
                    return
                }
            } else if (r.contains('<')) {
                val (prop, valueS) = r.substringBefore(':').split('<')
                val dst = r.substringAfter(':')
                val value = valueS.toInt()

                val propRange = curRange[prop]
                if (value > propRange.first) {
                    val okRange = curRange.withUpdated(prop, IntRange(propRange.first, min(value - 1, propRange.last)))
                    go(dst, okRange)
                }

                if (value <= propRange.last) {
                    curRange = curRange.withUpdated(prop, IntRange(max(value, propRange.first), propRange.last))
                } else {
                    return
                }
            } else {
                go(r, curRange)
            }
        }
    }

    fun part2(): Long {
        val range = PartRange(hashMapOf("x" to 1..4000, "m" to 1..4000, "a" to 1..4000, "s" to 1..4000))
        go("in", range)
        return total
    }

    measure { part2() }
}
